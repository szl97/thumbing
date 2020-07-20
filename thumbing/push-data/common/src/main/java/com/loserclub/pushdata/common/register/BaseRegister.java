package com.loserclub.pushdata.common.register;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.loserclub.pushdata.common.config.BaseAppConfig;
import com.loserclub.pushdata.common.config.BaseZookeeperConfig;
import com.loserclub.pushdata.common.utils.zk.ZkUtils;
import com.loserclub.pushdata.common.utils.zk.listener.ZkStateListener;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.zookeeper.CreateMode;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author Stan Sai
 * @date 2020-07-10
 */
@Data
@Slf4j
public abstract class BaseRegister<T extends BaseZookeeperConfig, K extends BaseAppConfig> {

    private ZkUtils zkUtils = new ZkUtils();

    @Autowired
    private K appConfig;

    @Autowired
    private T zookeeperConfig;

    public void init() throws InterruptedException {
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
                        log.info("{} 连接zk成功", appConfig.getName());
                        try {
                            register();
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void reconnectedEvent(CuratorFramework curator, ConnectionState state) {
                        log.info("{} 重新连接zk成功", appConfig.getName());
                        try {
                            register();
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void lostEvent(CuratorFramework curator, ConnectionState state) {
                        log.info("{} 连接zk丢失", appConfig.getName());
                    }
                }
        );

    }

    public void destory() {
        deRegister();
    }

    private void register() throws JsonProcessingException {
        String root = getRootValue();
        String name = appConfig.getName();
        if (!zkUtils.checkExists(root)) {
            zkUtils.createNode(root, null, CreateMode.PERSISTENT);
        }
        String path = root + "/" + name;
        if (!zkUtils.checkExists(path)) {
            zkUtils.createNode(path, getInfo(), CreateMode.EPHEMERAL);
        } else {
            zkUtils.setNodeData(path, getInfo());
        }
    }

    private void deRegister() {
        String root = getRootValue();
        String name = appConfig.getName();
        String path = root + "/" + name;
        if (zkUtils.checkExists(path)) {
            zkUtils.deleteNode(path);
        }
    }

    protected abstract String getRootValue();

    protected abstract String getInfo() throws JsonProcessingException;
}
