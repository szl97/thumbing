package com.loserclub.pushdata.common.Infos;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DataCenterInfo {
    private String name;
    private String ip;
    private int port;
    private int messagePort;
}
