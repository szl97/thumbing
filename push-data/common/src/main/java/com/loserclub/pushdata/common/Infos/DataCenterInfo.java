package com.loserclub.pushdata.common.Infos;

import lombok.Data;

@Data
public class DataCenterInfo {
    private String name;
    private String ip;
    private int port;
    private int messagePort;
}
