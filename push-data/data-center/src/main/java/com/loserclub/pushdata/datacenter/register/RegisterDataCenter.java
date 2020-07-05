package com.loserclub.pushdata.datacenter.register;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loserclub.pushdata.common.Infos.DataCenterInfo;
import com.loserclub.pushdata.common.constants.ZkGroupEnum;
import com.loserclub.pushdata.common.utils.ip.IpUtils;
import com.loserclub.pushdata.common.utils.zk.ZkUtils;
import com.loserclub.pushdata.common.utils.zk.listener.ZkStateListener;
import com.loserclub.pushdata.datacenter.config.DataCenterConfig;
import com.loserclub.pushdata.datacenter.config.ZookeeperConfig;
import com.loserclub.pushdata.datacenter.data.DataFlowBootStrap;
import com.loserclub.pushdata.datacenter.monitors.ServerConnectMonitorBootStrap;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.zookeeper.CreateMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Slf4j
@Component
@Data
public class RegisterDataCenter {

    @Autowired
    private ObjectMapper objectMapper;

    private ZkUtils zkUtils;

    @Autowired
    private DataCenterConfig dataCenterConfig;

    @Autowired
    private ZookeeperConfig zookeeperConfig;

    @Autowired
    private ServerConnectMonitorBootStrap serverConnectMonitorBootStrap;

    @Autowired
    private DataFlowBootStrap dataFlowBootStrap;

    @PostConstruct
    public void init() throws InterruptedException {
        serverConnectMonitorBootStrap.init();
        dataFlowBootStrap.init();
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
                        log.info("Data Center 连接zk成功");
                        try {
                            register();
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void reconnectedEvent(CuratorFramework curator, ConnectionState state) {
                        log.info("Data Center 连接连接zk成功");
                        try {
                            register();
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void lostEvent(CuratorFramework curator, ConnectionState state) {
                        log.info("Data Center 连接zk丢失");
                    }
                }
        );

    }

    @PreDestroy
    public void destory(){
        deRegister();
    }

    private void register() throws JsonProcessingException {
        String root = ZkGroupEnum.DATA_CENTER.getValue();
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
            zkUtils.createNode(root, objectMapper.writeValueAsString(info), CreateMode.EPHEMERAL);
        }
    }

    public void deRegister(){
        String root = ZkGroupEnum.DATA_CENTER.getValue();
        String name = dataCenterConfig.getName();
        String path = root+"/"+name;
        if(zkUtils.checkExists(path)){
            zkUtils.deleteNode(path);
        }
    }
}
