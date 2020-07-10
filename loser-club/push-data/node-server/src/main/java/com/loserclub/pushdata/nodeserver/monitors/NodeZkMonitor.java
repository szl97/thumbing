package com.loserclub.pushdata.nodeserver.monitors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loserclub.pushdata.common.Infos.DataCenterInfo;
import com.loserclub.pushdata.common.constants.ZkGroupEnum;
import com.loserclub.pushdata.common.utils.zk.ZkUtils;
import com.loserclub.pushdata.common.utils.zk.listener.ZkStateListener;
import com.loserclub.pushdata.nodeserver.client.DataFlowToCenterBootStrap;
import com.loserclub.pushdata.nodeserver.client.SyncConnectClientBootStrap;
import com.loserclub.pushdata.nodeserver.config.ZookeeperConfig;
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
public class NodeZkMonitor {

    @Autowired
    private ObjectMapper objectMapper;

    private ConcurrentHashMap<String, DataCenterInfo> centerPool = new ConcurrentHashMap<>(16);

    private ZkUtils zkUtils = new ZkUtils();

    @Autowired
    SyncConnectClientBootStrap syncConnectClientBootStrap;

    @Autowired
    DataFlowToCenterBootStrap dataFlowToCenterBootStrap;

    @Autowired
    private ZookeeperConfig zookeeperConfig;

    @PostConstruct
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
                        initCenterServerDiscovery();
                    }

                    @Override
                    public void reconnectedEvent(CuratorFramework curator, ConnectionState state) {
                        log.info("DataCenterDiscovery 重新连接zk成功");
                        initCenterServerDiscovery();
                    }

                    @Override
                    public void lostEvent(CuratorFramework curator, ConnectionState state) {
                        log.info("DataCenterDiscovery 连接zk丢失");
                        centerPool.clear();
                    }
                }
        );
        listenCenterServerDiscovery();

    }

    @PreDestroy
    public void destory() {
        centerPool.clear();
        zkUtils.destory();
    }

    /**
     * 获取当前现在的Center -Data 服务列表
     *
     * @return
     */
    public Map<String, DataCenterInfo> serverPool() {
        return new HashMap<>(centerPool);
    }

    /**
     * 初始化Center -Data 列表
     */
    private void initCenterServerDiscovery() {
        centerPool.clear();
        Map<String, String> datas = zkUtils.readTargetChildsData(ZkGroupEnum.DATA_CENTER.getValue());
        if (datas != null) {
            datas.forEach((k, v) -> {
                try {
                    DataCenterInfo data = objectMapper.readValue(v, DataCenterInfo.class);
                    centerPool.put(k, data);
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
    private void listenCenterServerDiscovery() {
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

    private void updateEvent(PathChildrenCacheEvent event) throws IOException {
        DataCenterInfo data = toDataCenterInfo(event);
        String key = data.getName();
        log.debug("center event update! key:{}, data:{}", key, data);
        //只需要更新缓存数据就可以了
        if (centerPool.containsKey(key)) {
            centerPool.put(key, data);
        }
    }

    private void removeEvent(PathChildrenCacheEvent event) throws IOException {
        DataCenterInfo data = toDataCenterInfo(event);
        String key = data.getName();
        syncConnectClientBootStrap.DisConnect(data);
        dataFlowToCenterBootStrap.DisConnect(data);
        log.info("center event remove! key:{}, data:{}", key, data);
        if (centerPool.containsKey(key)) {
            //检测Node是否还存在，存在的话移除该Node
            centerPool.remove(key);
        }

    }

    private void addEvent(PathChildrenCacheEvent event) throws IOException {
        DataCenterInfo data = toDataCenterInfo(event);
        String key = data.getName();
        log.info("center event add! key:{}, data:{}", key, data);
        if (!centerPool.containsKey(key)) {
            //开启node,加入到管理器
            centerPool.put(key, data);
            syncConnectClientBootStrap.Connect(data, 5, 1);
            dataFlowToCenterBootStrap.Connect(data, 5, 1);
        } else {
            log.error("center already! {},{}", key, data);
        }
    }


    private String toKey(PathChildrenCacheEvent event) {
        String path = event.getData().getPath();
        return path.substring(path.lastIndexOf("/")).replaceAll("/", "");
    }

    private DataCenterInfo toDataCenterInfo(PathChildrenCacheEvent event) throws IOException {
        return objectMapper.readValue(event.getData().getData(), DataCenterInfo.class);
    }
}
