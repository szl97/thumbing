package com.loserclub.pushdata.common.Infos;


import lombok.Builder;
import lombok.Data;


/**
 * @author Stan Sai
 * @date 2020-06-20
 */
@Data
@Builder
public class DataCenterInfo {
    private String name;
    private String ip;
    private int port;
    private int messagePort;
}
