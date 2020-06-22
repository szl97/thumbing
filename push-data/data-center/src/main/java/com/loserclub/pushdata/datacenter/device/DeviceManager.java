package com.loserclub.pushdata.datacenter.device;

import com.loserclub.pushdata.common.concurrent.ConcurrentHashSet;
import com.loserclub.pushdata.datacenter.config.DataCenterConfig;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
@Data
public class DeviceManager {

    private ConcurrentHashMap<String, ConcurrentHashSet<String>> clientPool = new ConcurrentHashMap<>(16);

    private ConcurrentHashMap<String, AtomicInteger> connectCountMap = new ConcurrentHashMap<>(16);

    @Autowired
    private DataCenterConfig dataCenterConfig;


    @PreDestroy
    public void destory() {
        connectCountMap.clear();
        clientPool.clear();
    }

    public void addNodeServer(String name){
        connectCountMap.put(name, new AtomicInteger(0));
        clientPool.put(name, new ConcurrentHashSet(dataCenterConfig.getInitializedConnect()));
    }

    public void removeNodeServer(String name){
        if(isExists(name)){
            connectCountMap.remove(name);
            clientPool.remove(name);
        }
    }

    public void clear(){
        clientPool.clear();
        connectCountMap.clear();
    }

    public boolean isExists(String name){
        return connectCountMap.keySet().contains(name);
    }


    public void addOrUpdateClient(String deviceId, String name){
        Set<Map.Entry<String,ConcurrentHashSet<String>>> entrys = clientPool.entrySet();
        entrys.forEach(
               e->{
                   if(!e.getKey().equals(name)){
                       ConcurrentHashSet set = e.getValue();
                       if(set.contains(deviceId)){
                           set.remove(deviceId);
                           connectCountMap.get(e.getKey()).decrementAndGet();
                       }
                   }
               }
        );
        ConcurrentHashSet set = clientPool.get(name);
        if(set != null) {
            set.add(deviceId);
            connectCountMap.get(name).incrementAndGet();
        }
    }

    public boolean removeClient(String deviceId, String name){
        ConcurrentHashSet set = clientPool.get(name);
        if(set != null) {
            if(set.contains(deviceId)){
                set.remove(deviceId);
                connectCountMap.get(name).decrementAndGet();
                return true;
            }
        }
        return false;
    }

    public String getNodeServer(String deviceId){
        Set<Map.Entry<String,ConcurrentHashSet<String>>> entrys = clientPool.entrySet();
        for(Map.Entry<String,ConcurrentHashSet<String>> e : entrys){
            ConcurrentHashSet set = e.getValue();
            if(set.contains(deviceId)){
                return e.getKey();
            }
        }
        return null;
    }

}
