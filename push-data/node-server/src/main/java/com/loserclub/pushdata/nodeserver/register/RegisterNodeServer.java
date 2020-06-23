package com.loserclub.pushdata.nodeserver.register;

import com.alibaba.fastjson.JSON;
import com.loserclub.pushdata.common.Infos.DataCenterInfo;
import com.loserclub.pushdata.common.constants.ZkGroupEnum;
import com.loserclub.pushdata.common.utils.ip.IpUtils;
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
 * @author Stan Sai
 * @date 2020-06-22
 */
@Slf4j
@Component
@Data
public class RegisterNodeServer {
    private ZkUtils zkUtils;

    @Autowired
    private NodeServerConfig dataCenterConfig;

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
                        log.info("NodeServer 连接zk成功");
                        register();
                    }

                    @Override
                    public void reconnectedEvent(CuratorFramework curator, ConnectionState state) {
                        log.info("NodeServer 重新连接zk成功");
                        register();
                    }

                    @Override
                    public void lostEvent(CuratorFramework curator, ConnectionState state) {
                        log.info("NodeServer 连接zk丢失");
                    }
                }
        );

    }

    @PreDestroy
    public void destory(){
        deRegister();
    }

    private void register(){
        String root = ZkGroupEnum.NODE_SERVER.getValue();
        String name = dataCenterConfig.getName();
        if(!zkUtils.checkExists(root)){
            zkUtils.createNode(root,null, CreateMode.PERSISTENT);
        }
        DataCenterInfo info = DataCenterInfo.builder()
                .ip(IpUtils.internetIp())
                .name(name)
                .port(dataCenterConfig.getPort())
                .messagePort(dataCenterConfig.getMessagePort())
                .build();
        String path = root+"/"+name;
        if(!zkUtils.checkExists(path)){
            zkUtils.createNode(root, JSON.toJSONString(info), CreateMode.EPHEMERAL);
        }
    }

    public void deRegister(){
        String root = ZkGroupEnum.NODE_SERVER.getValue();
        String name = dataCenterConfig.getName();
        String path = root+"/"+name;
        if(zkUtils.checkExists(path)){
            zkUtils.deleteNode(path);
        }
    }
}
