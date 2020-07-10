package com.loserclub.pushdata.datacenter.register;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loserclub.pushdata.common.Infos.DataCenterInfo;
import com.loserclub.pushdata.common.constants.ZkGroupEnum;
import com.loserclub.pushdata.common.register.BaseRegister;
import com.loserclub.shared.utils.ip.*;
import com.loserclub.pushdata.common.utils.zk.ZkUtils;
import com.loserclub.pushdata.common.utils.zk.listener.ZkStateListener;
import com.loserclub.pushdata.datacenter.config.DataCenterConfig;
import com.loserclub.pushdata.datacenter.config.ZookeeperConfig;
import com.loserclub.pushdata.datacenter.server.DataFlowBootStrap;
import com.loserclub.pushdata.datacenter.server.ServerConnectMonitorBootStrap;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;


/**
 * 启动data-center服务后，在zookeeper上注册自己的地址信息
 *
 * @author Stan Sai
 * @date 2020-06-20
 */
@Slf4j
@Component
@Data
public class RegisterDataCenter extends BaseRegister<ZookeeperConfig, DataCenterConfig> {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ServerConnectMonitorBootStrap serverConnectMonitorBootStrap;

    @Autowired
    private DataFlowBootStrap dataFlowBootStrap;

    @PostConstruct
    public void init() throws InterruptedException {
        serverConnectMonitorBootStrap.init();
        dataFlowBootStrap.init();
        super.init();
    }

    @PreDestroy
    public void destory() {
        super.destory();
    }


    @Override
    protected String getRootValue() {
        return ZkGroupEnum.DATA_CENTER.getValue();
    }

    @Override
    protected String getInfo() throws JsonProcessingException {
        DataCenterInfo info = DataCenterInfo.builder()
                .ip(IpUtils.getIp())
                .name(getAppConfig().getName())
                .port(getAppConfig().getPort())
                .messagePort(getAppConfig().getMessagePort())
                .build();
        return objectMapper.writeValueAsString(info);
    }
}
