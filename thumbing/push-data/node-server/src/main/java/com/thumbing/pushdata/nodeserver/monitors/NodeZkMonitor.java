package com.thumbing.pushdata.nodeserver.monitors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thumbing.pushdata.common.Infos.DataCenterInfo;
import com.thumbing.pushdata.common.constants.ZkGroupEnum;
import com.thumbing.pushdata.common.monitors.BaseMonitor;
import com.thumbing.pushdata.common.utils.zk.ZkUtils;
import com.thumbing.pushdata.common.utils.zk.listener.ZkStateListener;
import com.thumbing.pushdata.nodeserver.client.DataFlowToCenterBootStrap;
import com.thumbing.pushdata.nodeserver.client.SyncConnectClientBootStrap;
import com.thumbing.pushdata.nodeserver.config.ZookeeperConfig;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.state.ConnectionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 获取目前已经注册的data-center，记录他们的地址
 * 并监听data-center的变化
 *
 * @author Stan Sai
 * @date 2020-06-22
 */
@Slf4j
@Component
@Data
public class NodeZkMonitor extends BaseMonitor<ZookeeperConfig, DataCenterInfo> {

    @Autowired
    private ObjectMapper objectMapper;

    private ConcurrentHashMap<String, DataCenterInfo> pool = new ConcurrentHashMap<>(16);

    private ZkUtils zkUtils = new ZkUtils();

    @Autowired
    SyncConnectClientBootStrap syncConnectClientBootStrap;

    @Autowired
    DataFlowToCenterBootStrap dataFlowToCenterBootStrap;

    @Autowired
    private ZookeeperConfig zookeeperConfig;

    @PostConstruct
    @Override
    public void init() {
        zkUtils.init(
                zookeeperConfig.getServers(),
                zookeeperConfig.getConnectionTimeout(),
                zookeeperConfig.getSessionTimeout(),
                zookeeperConfig.getMaxRetries(),
                zookeeperConfig.getRetriesSleepTime(),
                zookeeperConfig.getNamespace(),
                new ZkStateListener() {
                    @Override
                    public void connectedEvent(CuratorFramework curator, ConnectionState state) {
                        log.info("DataCenterDiscovery 连接zk成功");
                        initDiscovery();
                    }

                    @Override
                    public void reconnectedEvent(CuratorFramework curator, ConnectionState state) {
                        log.info("DataCenterDiscovery 重新连接zk成功");
                        initDiscovery();
                    }

                    @Override
                    public void lostEvent(CuratorFramework curator, ConnectionState state) {
                        log.info("DataCenterDiscovery 连接zk丢失");
                        pool.clear();
                    }
                }
        );
        listenDiscovery();

    }

    @PreDestroy
    @Override
    public void destroy() {
        pool.clear();
        zkUtils.destroy();
    }

    /**
     * 获取当前现在的Center -Data 服务列表
     *
     * @return
     */
    @Override
    public Map<String, DataCenterInfo> pool() {
        return new HashMap<>(pool);
    }

    /**
     * 初始化Center -Data 列表
     */
    @Override
    protected void initDiscovery() {
        pool.clear();
        Map<String, String> datas = zkUtils.readTargetChildsData(ZkGroupEnum.DATA_CENTER.getValue());
        if (datas != null) {
            datas.forEach((k, v) -> {
                try {
                    DataCenterInfo data = objectMapper.readValue(v, DataCenterInfo.class);
                    pool.put(k, data);
                    syncConnectClientBootStrap.Connect(data, 5, 1);
                    dataFlowToCenterBootStrap.Connect(data, 5, 1);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    /**
     * 设置监听发生更新，更新缓存数据，发生新增，删除，更新
     */
    @Override
    protected void listenDiscovery() {
        zkUtils.listenerPathChildrenCache(ZkGroupEnum.DATA_CENTER.getValue(), ((client, event) -> {
            switch (event.getType()) {
                case CHILD_ADDED:
                    try {
                        addEvent(event);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case CHILD_REMOVED:
                    try {
                        removeEvent(event);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case CHILD_UPDATED:
                    try {
                        updateEvent(event);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        }));
    }

    @Override
    protected void updateEvent(PathChildrenCacheEvent event) throws IOException {
        DataCenterInfo data = toInfo(event);
        String key = data.getName();
        log.debug("center event update! key:{}, data:{}", key, data);
        //只需要更新缓存数据就可以了
        if (pool.containsKey(key)) {
            pool.put(key, data);
        }
    }

    @Override
    protected void removeEvent(PathChildrenCacheEvent event) throws IOException {
        DataCenterInfo data = toInfo(event);
        String key = data.getName();
        syncConnectClientBootStrap.DisConnect(data);
        dataFlowToCenterBootStrap.DisConnect(data);
        log.info("center event remove! key:{}, data:{}", key, data);
        if (pool.containsKey(key)) {
            //检测Node是否还存在，存在的话移除该Node
            pool.remove(key);
        }

    }

    @Override
    protected void addEvent(PathChildrenCacheEvent event) throws IOException {
        DataCenterInfo data = toInfo(event);
        String key = data.getName();
        log.info("center event add! key:{}, data:{}", key, data);
        if (!pool.containsKey(key)) {
            //开启node,加入到管理器
            pool.put(key, data);
            syncConnectClientBootStrap.Connect(data, 5, 1);
            dataFlowToCenterBootStrap.Connect(data, 5, 1);
        } else {
            log.error("center already! {},{}", key, data);
        }
    }

    @Override
    protected String toKey(PathChildrenCacheEvent event) {
        String path = event.getData().getPath();
        return path.substring(path.lastIndexOf("/")).replaceAll("/", "");
    }

    @Override
    protected DataCenterInfo toInfo(PathChildrenCacheEvent event) throws IOException {
        return objectMapper.readValue(event.getData().getData(), DataCenterInfo.class);
    }

    @Override
    protected String getIpWithPort(String name) throws IOException {
        return null;
    }
}
