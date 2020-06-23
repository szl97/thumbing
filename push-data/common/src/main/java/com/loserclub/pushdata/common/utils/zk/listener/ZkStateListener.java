package com.loserclub.pushdata.common.utils.zk.listener;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.state.ConnectionState;

/**
 * @author Stan Sai
 * @date 2020-06-20
 */

public interface ZkStateListener {

    default void connectedEvent(CuratorFramework curator, ConnectionState state) {
    }

    default void reconnectedEvent(CuratorFramework curator, ConnectionState state) {
    }

    default void lostEvent(CuratorFramework curator, ConnectionState state) {
    }
}
