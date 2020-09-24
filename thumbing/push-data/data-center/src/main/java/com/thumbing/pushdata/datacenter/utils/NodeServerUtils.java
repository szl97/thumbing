package com.thumbing.pushdata.datacenter.utils;

import com.thumbing.pushdata.common.cache.DeviceCache;
import com.thumbing.shared.utils.wrap.WrapObject;
import lombok.experimental.UtilityClass;

import java.util.Set;

/**
 * @Author: Stan Sai
 * @Date: 2020/9/24 15:15
 */
@UtilityClass
public class NodeServerUtils {
    public String getNodeServer(DeviceCache cache, Set<String> nodes, Long deviceId){
        WrapObject<String> name = new WrapObject<>();
        nodes.stream().forEach(n-> {
                    if (cache.contains(n, deviceId)){
                        name.setValue(n);
                    }
                }
        );
        return name.getValue();
    }
}
