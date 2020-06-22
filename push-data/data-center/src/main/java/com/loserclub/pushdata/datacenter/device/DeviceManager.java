package com.loserclub.pushdata.datacenter.device;

import com.loserclub.pushdata.datacenter.config.DataCenterConfig;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@Data
public class DeviceManager {

    private ConcurrentHashMap<String, String> clientPool;

    @Autowired
    private DataCenterConfig dataCenterConfig;

    @PostConstruct
    public void init(){

        clientPool = new ConcurrentHashMap<>(dataCenterConfig.getInitializedConnect());
    }

    public void addOrUpdateClient(String deviceId, String name){
        clientPool.put(deviceId,name);
    }

    public boolean removeClient(String deviceId, String name){
        if(clientPool.keySet().contains(deviceId)){
            if(clientPool.get(deviceId).equals(name)){
                clientPool.remove(deviceId);
                return true;
            }
        }
        return false;
    }

    public String getNodeServer(String deviceId){
        return clientPool.get(deviceId);
    }

    /**
     * 获取当前现在的Client -Server 服务列表
     *
     * @return
     */
    public Map<String, String> clientPool() {
        return new HashMap<>(clientPool);
    }
}
