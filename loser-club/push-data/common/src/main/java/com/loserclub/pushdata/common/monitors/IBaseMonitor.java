package com.loserclub.pushdata.common.monitors;

import com.loserclub.pushdata.common.Infos.BaseAppInfo;

import java.util.Map;
/**
 * @author Stan Sai
 * @date 2020-07-10
 */
public interface IBaseMonitor<T extends BaseAppInfo> {
    void init();

    void destory();

    Map<String, T> pool();
}
