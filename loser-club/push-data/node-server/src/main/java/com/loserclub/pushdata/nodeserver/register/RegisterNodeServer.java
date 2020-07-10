package com.loserclub.pushdata.nodeserver.register;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loserclub.pushdata.common.Infos.DataCenterInfo;
import com.loserclub.pushdata.common.constants.ZkGroupEnum;
import com.loserclub.shared.utils.ip.*;
import com.loserclub.pushdata.common.utils.zk.ZkUtils;
import com.loserclub.pushdata.common.utils.zk.listener.ZkStateListener;
import com.loserclub.pushdata.nodeserver.config.NodeServerConfig;
import com.loserclub.pushdata.nodeserver.config.ZookeeperConfig;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.zookeeper.CreateMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;


/**
 * 启动node-server服务后，在zookeeper上注册自己的地址信息
 *
 * @author Stan Sai
 * @date 2020-06-22
 */
@Slf4j
@Component
@Data
public class RegisterNodeServer {
    @Autowired
    private ObjectMapper objectMapper;

    private ZkUtils zkUtils = new ZkUtils();

    @Autowired
    private NodeServerConfig nodeServerConfig;

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
                        log.info("NodeServer 连接zk成功");
                        try {
                            register();
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void reconnectedEvent(CuratorFramework curator, ConnectionState state) {
                        log.info("NodeServer 重新连接zk成功");
                        try {
                            register();
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void lostEvent(CuratorFramework curator, ConnectionState state) {
                        log.info("NodeServer 连接zk丢失");
                    }
                }
        );

    }

    @PreDestroy
    public void destory() {
        deRegister();
    }

    private void register() throws JsonProcessingException {
        String root = ZkGroupEnum.NODE_SERVER.getValue();
        String name = nodeServerConfig.getName();
        if (!zkUtils.checkExists(root)) {
            zkUtils.createNode(root, null, CreateMode.PERSISTENT);
        }
        DataCenterInfo info = DataCenterInfo.builder()
                .ip(IpUtils.getIp())
                .name(name)
                .port(nodeServerConfig.getPort())
                .messagePort(nodeServerConfig.getMessagePort())
                .build();
        String path = root + "/" + name;
        if (!zkUtils.checkExists(path)) {
            zkUtils.createNode(path, objectMapper.writeValueAsString(info), CreateMode.EPHEMERAL);
        } else {
            zkUtils.setNodeData(path, objectMapper.writeValueAsString(info));
        }
    }

    public void deRegister() {
        String root = ZkGroupEnum.NODE_SERVER.getValue();
        String name = nodeServerConfig.getName();
        String path = root + "/" + name;
        if (zkUtils.checkExists(path)) {
            zkUtils.deleteNode(path);
        }
    }
}
