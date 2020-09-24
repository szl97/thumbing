package com.thumbing.pushdata.datacenter.service;

import cn.hutool.core.util.ArrayUtil;
import com.sun.istack.Nullable;
import com.thumbing.pushdata.common.cache.DeviceCache;
import com.thumbing.pushdata.datacenter.monitors.CenterZkMonitor;
import com.thumbing.shared.exception.BusinessException;
import com.thumbing.shared.utils.wrap.WrapObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;


/**
 * 提供最空闲的node-server给客户端
 *
 * @author Stan Sai
 * @date 2020-06-23
 */
@Service
public class DataFlowService implements IDataFlowService {

    @Autowired
    private CenterZkMonitor centerZkMonitor;

    @Autowired
    private DeviceCache cache;

    @Override
    public String balancedLoadServer() {
        String ipWithPort = getBalancedLoader(balancedLoader());
        if (ipWithPort == null) throw new BusinessException("服务器暂时无法提供服务");
        return ipWithPort;
    }

    private String getBalancedLoader(String name, @Nullable String... exception) {
        if (name == null)
            return null;
        String ipWithPort = centerZkMonitor.getIpWithPort(name);
        if (ipWithPort == null) {
            if(exception == null) {
                return getBalancedLoader(balancedLoader(name));
            }
            else {
                return getBalancedLoader(balancedLoader(ArrayUtil.append(exception.clone(),name)));
            }
        } else {
            return ipWithPort;
        }
    }

    private String balancedLoader(@Nullable String... exception) {
        WrapObject<String[]> e = new WrapObject<>();
        if (exception != null) {
            e.setValue(exception.clone());
        }
        Set<String> nodes = centerZkMonitor.getAllNodes();
        WrapObject<String> name = new WrapObject<>();
        WrapObject<Long> count = new WrapObject<>(Long.MAX_VALUE);
        nodes.stream().forEach(n -> {
                if (e.getValue() == null || !ArrayUtil.contains(e.getValue(), n)) {
                    Long c = cache.count(n);
                    if (c < count.getValue()) {
                            count.setValue(c);
                            name.setValue(n);
                        }
                    }
                }
        );
        return name.getValue();
    }
}
