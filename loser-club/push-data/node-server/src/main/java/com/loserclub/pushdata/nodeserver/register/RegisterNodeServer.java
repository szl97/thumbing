package com.loserclub.pushdata.nodeserver.register;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loserclub.pushdata.common.Infos.DataCenterInfo;
import com.loserclub.pushdata.common.Infos.NodeServerInfo;
import com.loserclub.pushdata.common.constants.ZkGroupEnum;
import com.loserclub.pushdata.common.register.BaseRegister;
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
public class RegisterNodeServer extends BaseRegister<ZookeeperConfig, NodeServerConfig>{
    @Autowired
    private ObjectMapper objectMapper;

    @PostConstruct
    public void init() throws InterruptedException {
        super.init();
    }

    @PreDestroy
    public void destory() {
        super.destory();
    }

    @Override
    protected String getRootValue() {
        return ZkGroupEnum.NODE_SERVER.getValue();
    }

    @Override
    protected String getInfo() throws JsonProcessingException {
        NodeServerInfo info = NodeServerInfo.builder()
                .ip(IpUtils.getIp())
                .name(getAppConfig().getName())
                .port(getAppConfig().getPort())
                .messagePort(getAppConfig().getMessagePort())
                .devicePort(getAppConfig().getDevicePort())
                .build();
        return objectMapper.writeValueAsString(info);
    }
}
