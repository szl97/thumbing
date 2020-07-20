package com.loserclub.pushdata.datacenter.monitors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loserclub.pushdata.common.Infos.NodeServerInfo;
import com.loserclub.pushdata.common.constants.ZkGroupEnum;
import com.loserclub.pushdata.common.monitors.BaseMonitor;
import com.loserclub.pushdata.common.utils.zk.ZkUtils;
import com.loserclub.pushdata.common.utils.zk.listener.ZkStateListener;
import com.loserclub.pushdata.datacenter.config.ZookeeperConfig;
import com.loserclub.pushdata.datacenter.device.DeviceManager;
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
 * 获取目前已经注册的node-server，记录他们的地址
 * 并监听node-server的变化
 *
 * @author Stan Sai
 * @date 2020-06-20
 */
@Slf4j
@Component
@Data
public class CenterZkMonitor extends BaseMonitor<ZookeeperConfig, NodeServerInfo> {

    @Autowired
    private ObjectMapper objectMapper;

    private ConcurrentHashMap<String, NodeServerInfo> pool = new ConcurrentHashMap<>(16);

    private ZkUtils zkUtils = new ZkUtils();

    @Autowired
    private DeviceManager deviceManager;

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
                        log.info("NodeServerDiscovery 连接zk成功");
                        initDiscovery();
                    }

                    @Override
                    public void reconnectedEvent(CuratorFramework curator, ConnectionState state) {
                        log.info("NodeServerDiscovery 重新连接zk成功");
                        initDiscovery();
                    }

                    @Override
                    public void lostEvent(CuratorFramework curator, ConnectionState state) {
                        log.info("NodeServerDiscovery 连接zk丢失");
                        pool.clear();
                    }
                }
        );
        listenDiscovery();

    }

    @PreDestroy
    @Override
    public void destory() {
        pool.clear();
        zkUtils.destory();
    }

    /**
     * 获取当前现在的Node -Server 服务列表
     *
     * @return
     */
    @Override
    public Map<String, NodeServerInfo> pool() {
        return new HashMap<>(pool);
    }

    /**
     * 初始化node-server列表
     */
    @Override
    protected void initDiscovery() {
        pool.clear();
        deviceManager.clear();
        Map<String, String> datas = zkUtils.readTargetChildsData(ZkGroupEnum.NODE_SERVER.getValue());
        if (datas != null) {
            datas.forEach((k, v) -> {
                try {
                    pool.put(k, objectMapper.readValue(v, NodeServerInfo.class));
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
        zkUtils.listenerPathChildrenCache(ZkGroupEnum.NODE_SERVER.getValue(), ((client, event) -> {
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
        NodeServerInfo data = toInfo(event);
        String key = data.getName();
        log.debug("node event update! key:{}, data:{}", key, data);
        //只需要更新缓存数据就可以了
        if (pool.containsKey(key)) {
            pool.put(key, data);
        }
    }

    @Override
    protected void removeEvent(PathChildrenCacheEvent event) throws IOException {
        NodeServerInfo data = toInfo(event);
        String key = data.getName();
        log.info("node event remove! key:{}, data:{}", key, data);
        if (pool.containsKey(key)) {
            //检测Node是否还存在，存在的话移除该Node
            pool.remove(key);
        }
        deviceManager.removeNodeServer(data.getName());

    }

    @Override
    protected void addEvent(PathChildrenCacheEvent event) throws IOException {
        NodeServerInfo data = toInfo(event);
        String key = data.getName();
        log.info("node event add! key:{}, data:{}", key, data);
        if (!pool.containsKey(key)) {
            //开启node,加入到管理器
            pool.put(key, data);
            deviceManager.addNodeServer(data.getName());
        } else {
            log.error("node already! {},{}", key, data);
        }
    }

    @Override
    protected String toKey(PathChildrenCacheEvent event) {
        String path = event.getData().getPath();
        return path.substring(path.lastIndexOf("/")).replaceAll("/", "");
    }

    @Override
    protected NodeServerInfo toInfo(PathChildrenCacheEvent event) throws IOException {
        return objectMapper.readValue(event.getData().getData(), NodeServerInfo.class);
    }

    public String getIpWithPort(String name) {
        NodeServerInfo info = pool.get(name);
        if (info == null) return null;
        return info.getIp() + ":" + info.getDevicePort();
    }

}
