package com.loserclub.pushdata.datacenter.monitors;

import com.alibaba.fastjson.JSON;
import com.loserclub.pushdata.common.Infos.NodeServerInfo;
import com.loserclub.pushdata.common.constants.ZkGroupEnum;
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
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@Data
public class CenterZkMonitor {

    private ConcurrentHashMap<String, NodeServerInfo> serverPool = new ConcurrentHashMap<>(16);

    private ZkUtils zkUtils;

    @Autowired
    private DeviceManager deviceManager;

    @Autowired
    private ZookeeperConfig zookeeperConfig;

    @PostConstruct
    public void init(){
        zkUtils.init(
                zookeeperConfig.getServers(),
                zookeeperConfig.getConnectionTimeout(),
                zookeeperConfig.getSessionTimeout(),
                zookeeperConfig.getMaxRetries(),
                zookeeperConfig.getRetriesSleepTime(),
                zookeeperConfig.getListenNamespace(),
                new ZkStateListener() {
                    @Override
                    public void connectedEvent(CuratorFramework curator, ConnectionState state) {
                        log.info("NodeServerDiscovery 链接zk成功");
                        initNodeServerDiscovery();
                    }

                    @Override
                    public void reconnectedEvent(CuratorFramework curator, ConnectionState state) {
                        log.info("NodeServerDiscovery 重新链接zk成功");
                        initNodeServerDiscovery();
                    }

                    @Override
                    public void lostEvent(CuratorFramework curator, ConnectionState state) {
                        log.info("NodeServerDiscovery 链接zk丢失");
                        serverPool.clear();
                    }
                }
        );
        listenNodeServerDiscovery();

    }
    @PreDestroy
    public void destory() {
        serverPool.clear();
        zkUtils.destory();
    }

    /**
     * 获取当前现在的Node -Server 服务列表
     *
     * @return
     */
    public Map<String, NodeServerInfo> serverPool() {
        return new HashMap<>(serverPool);
    }

    /**
     * 初始化node-server列表
     */
    private void initNodeServerDiscovery() {
        serverPool.clear();
        deviceManager.clear();
        Map<String, String> datas = zkUtils.readTargetChildsData(ZkGroupEnum.NODE_SERVER.getValue());
        if (datas != null) {
            datas.forEach((k, v) -> serverPool.put(k, JSON.parseObject(v, NodeServerInfo.class)));
        }
    }

    /**
     * 设置监听发生更新，更新缓存数据，发生新增，删除，更新
     */
    private void listenNodeServerDiscovery() {
        zkUtils.listenerPathChildrenCache(ZkGroupEnum.NODE_SERVER.getValue(), ((client, event) -> {
            switch (event.getType()) {
                case CHILD_ADDED:
                    addEvent(event);
                    break;
                case CHILD_REMOVED:
                    removeEvent(event);
                    break;
                case CHILD_UPDATED:
                    updateEvent(event);
                    break;
                default:
                    break;
            }
        }));
    }

    private void updateEvent(PathChildrenCacheEvent event) {
        NodeServerInfo data = toNodeServerInfo(event);
        String key = data.getName();
        log.debug("node event update! key:{}, data:{}", key, data);
        //只需要更新缓存数据就可以了
        if (serverPool.containsKey(key)) {
            serverPool.put(key, data);
        }
    }

    private void removeEvent(PathChildrenCacheEvent event) {
        NodeServerInfo data = toNodeServerInfo(event);
        String key = data.getName();
        log.info("node event remove! key:{}, data:{}", key, data);
        if (serverPool.containsKey(key)) {
            //检测Node是否还存在，存在的话移除该Node
            serverPool.remove(key);
        }
        deviceManager.removeNodeServer(data.getName());

    }

    private void addEvent(PathChildrenCacheEvent event) {
        NodeServerInfo data = toNodeServerInfo(event);
        String key = data.getName();
        log.info("node event add! key:{}, data:{}", key, data);
        if (!serverPool.containsKey(key)) {
            //开启node,加入到管理器
            serverPool.put(key, data);
            deviceManager.addNodeServer(data.getName());
        } else {
            log.error("node already! {},{}", key, data);
        }
    }


    private String toKey(PathChildrenCacheEvent event) {
        String path = event.getData().getPath();
        return path.substring(path.lastIndexOf("/")).replaceAll("/", "");
    }

    private NodeServerInfo toNodeServerInfo(PathChildrenCacheEvent event) {
        return JSON.parseObject(event.getData().getData(), NodeServerInfo.class);
    }

}
