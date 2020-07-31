package com.thumbing.pushdata.nodeserver.register;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thumbing.pushdata.common.Infos.NodeServerInfo;
import com.thumbing.pushdata.common.constants.ZkGroupEnum;
import com.thumbing.pushdata.common.register.BaseRegister;
import com.thumbing.pushdata.nodeserver.config.NodeServerConfig;
import com.thumbing.pushdata.nodeserver.config.ZookeeperConfig;
import com.thumbing.shared.utils.ip.IpUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
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
