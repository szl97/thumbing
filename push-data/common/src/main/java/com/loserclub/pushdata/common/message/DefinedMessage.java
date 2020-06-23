package com.loserclub.pushdata.common.message;



/**
 * @author Stan Sai
 * @date 2020-06-21
 */
public abstract class DefinedMessage<T> {
    /**r
     * 消息类型
     * 1)连接成功确认 收到Server Node的连接成功确认将Channel绑定Node Server Name并保存
     * 2)心跳请求
     * 3)心跳响应
     * 4)Node Server的消息推送(用于通知ClientMonitor，建立了新的Client连接，或者关闭了与某个Client的连接)
     * 5)回应
     * 6)消息推送
     * 7)长连接
     * 8)失败
     */
    protected enum Type {
        C,      // CONFIRM
        PI,     // PING
        PO,     // PONG
        P,      // PUSH_REQ
        PR,     // PUSH_RESP
        PD,     // PUSH_DATA
        HS,     // HandShake
        F,      // FAIL
        IN      // INACTIVE
    }

    /**
     * 获取设备消息类型
     *
     * @return
     */
    protected abstract Type type();


    protected abstract T getThis() throws Exception;

}
