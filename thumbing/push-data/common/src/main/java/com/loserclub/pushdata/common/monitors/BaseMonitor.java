package com.loserclub.pushdata.common.monitors;


import com.loserclub.pushdata.common.Infos.BaseAppInfo;
import com.loserclub.pushdata.common.config.BaseZookeeperConfig;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import java.io.IOException;

/**
 * @author Stan Sai
 * @date 2020-07-10
 */

@Slf4j
@Data
public abstract class BaseMonitor<T extends BaseZookeeperConfig, K extends BaseAppInfo> implements IBaseMonitor<K> {

    protected abstract void initDiscovery();

    protected abstract  void listenDiscovery();

    protected abstract  void updateEvent(PathChildrenCacheEvent event) throws IOException;

    protected abstract  void removeEvent(PathChildrenCacheEvent event) throws IOException;

    protected abstract  void addEvent(PathChildrenCacheEvent event) throws IOException;

    protected abstract  String toKey(PathChildrenCacheEvent event) throws IOException;

    protected abstract  K toInfo(PathChildrenCacheEvent event) throws IOException;

    protected abstract  String getIpWithPort(String name) throws IOException;
}
