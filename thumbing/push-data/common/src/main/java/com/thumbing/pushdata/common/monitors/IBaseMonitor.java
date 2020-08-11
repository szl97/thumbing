package com.thumbing.pushdata.common.monitors;

import com.thumbing.pushdata.common.Infos.BaseAppInfo;

import java.util.Map;
/**
 * @author Stan Sai
 * @date 2020-07-10
 */
public interface IBaseMonitor<T extends BaseAppInfo> {
    void init();

    void destroy();

    Map<String, T> pool();
}
