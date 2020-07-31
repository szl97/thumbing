package com.thumbing.pushdata.datacenter.service;

import com.thumbing.pushdata.datacenter.device.DeviceManager;
import com.thumbing.pushdata.datacenter.monitors.CenterZkMonitor;
import com.thumbing.shared.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 提供最空闲的node-server给客户端
 *
 * @author Stan Sai
 * @date 2020-06-23
 */
@Service
public class DataFlowService implements IDataFlowService {

    @Autowired
    DeviceManager deviceManager;

    @Autowired
    CenterZkMonitor centerZkMonitor;

    @Override
    public String balancedLoadServer() {
        String ipWithPort = getBalancedLoader(deviceManager.balancedLoader());
        if (ipWithPort == null) throw new BusinessException("服务器暂时无法提供服务");
        return ipWithPort;
    }

    private String getBalancedLoader(String name) {
        if (name == null)
            return null;
        String ipWithPort = centerZkMonitor.getIpWithPort(name);
        if (ipWithPort == null) {
            deviceManager.removeNodeServer(name);
            return getBalancedLoader(deviceManager.balancedLoader());
        } else {
            return ipWithPort;
        }
    }
}
