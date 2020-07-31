package com.thumbing.pushdata.datacenter.device;

import com.thumbing.pushdata.common.concurrent.ConcurrentHashSet;
import com.thumbing.pushdata.datacenter.config.DataCenterConfig;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author Stan Sai
 * @date 2020-06-22
 */
@Slf4j
@Component
@Data
public class DeviceManager {

    /**
     * 保存每个node-server连接的设备ID
     */
    private ConcurrentHashMap<String, ConcurrentHashSet<Long>> clientPool = new ConcurrentHashMap<>(16);

    @Autowired
    private DataCenterConfig dataCenterConfig;


    @PreDestroy
    public void destory() {
        clientPool.clear();
    }

    public void addNodeServer(String name) {
        if (!clientPool.contains(name)) {
            clientPool.put(name, new ConcurrentHashSet(dataCenterConfig.getInitializedConnect()));
        }
    }

    public void removeNodeServer(String name) {
        if (isExists(name)) {
            clientPool.remove(name);
        }
    }

    public void clear() {
        clientPool.clear();
    }

    public boolean isExists(String name) {
        return clientPool.keySet().contains(name);
    }


    public void addOrUpdateClient(Long deviceId, String name) {
        Set<Map.Entry<String, ConcurrentHashSet<Long>>> entrys = clientPool.entrySet();
        entrys.forEach(
                e -> {
                    if (!e.getKey().equals(name)) {
                        ConcurrentHashSet set = e.getValue();
                        if (set.contains(deviceId)) {
                            set.remove(deviceId);
                        }
                    }
                }
        );
        ConcurrentHashSet set = clientPool.get(name);
        if (set != null) {
            set.add(deviceId);
        }
    }

    public boolean removeClient(Long deviceId, String name) {
        ConcurrentHashSet set = clientPool.get(name);
        if (set != null) {
            if (set.contains(deviceId)) {
                set.remove(deviceId);
                return true;
            }
        }
        return false;
    }

    public String getNodeServer(Long deviceId) {
        Set<Map.Entry<String, ConcurrentHashSet<Long>>> entries = clientPool.entrySet();
        for (Map.Entry<String, ConcurrentHashSet<Long>> e : entries) {
            ConcurrentHashSet set = e.getValue();
            if (set.contains(deviceId)) {
                return e.getKey();
            }
        }
        return null;
    }

    public String balancedLoader() {
        int max = Integer.MAX_VALUE;
        String name = null;
        Set<Map.Entry<String, ConcurrentHashSet<Long>>> entries = clientPool.entrySet();
        for (Map.Entry<String, ConcurrentHashSet<Long>> entry : entries) {
            if (entry.getValue().size() < max) {
                max = entry.getValue().size();
                name = entry.getKey();
            }
        }
        return name;
    }

}
