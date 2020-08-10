package com.thumbing.pushdata.datacenter.device;

import com.thumbing.pushdata.datacenter.config.DataCenterConfig;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;


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
    private ConcurrentSkipListMap<String, ConcurrentSkipListSet<Long>> clientPool = new ConcurrentSkipListMap<>();
    @Autowired
    private DataCenterConfig dataCenterConfig;


    @PreDestroy
    public void destory() {
        clientPool.clear();
    }

    public void addNodeServer(String name) {
        if (!clientPool.containsKey(name)) {
            clientPool.put(name, new ConcurrentSkipListSet());
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
        clientPool.entrySet().forEach(
                e -> {
                    if (!e.getKey().equals(name)) {
                        ConcurrentSkipListSet set = e.getValue();
                        if (set.contains(deviceId)) {
                            set.remove(deviceId);
                        }
                    }
                }
        );
        ConcurrentSkipListSet set = clientPool.get(name);
        if (set != null) {
            set.add(deviceId);
        }
    }

    public boolean removeClient(Long deviceId, String name) {
        ConcurrentSkipListSet set = clientPool.get(name);
        if (set != null) {
            if (set.contains(deviceId)) {
                set.remove(deviceId);
                return true;
            }
        }
        return false;
    }

    public String getNodeServer(Long deviceId) {
        Set<Map.Entry<String, ConcurrentSkipListSet<Long>>> entries = clientPool.entrySet();
        for (Map.Entry<String, ConcurrentSkipListSet<Long>> e : entries) {
            ConcurrentSkipListSet set = e.getValue();
            if (set.contains(deviceId)) {
                return e.getKey();
            }
        }
        return null;
    }

    public String balancedLoader() {
        int max = Integer.MAX_VALUE;
        String name = null;
        Set<Map.Entry<String, ConcurrentSkipListSet<Long>>> entries = clientPool.entrySet();
        for (Map.Entry<String, ConcurrentSkipListSet<Long>> entry : entries) {
            if (entry.getValue().size() < max) {
                max = entry.getValue().size();
                name = entry.getKey();
            }
        }
        return name;
    }

}
