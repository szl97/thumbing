package com.loserclub.pushdata.common.Infos;

import lombok.Data;

@Data
public class NodeServerInfo {
    private String name;
    private String ip;
    private int devicePort;
    private int nodePort;
}
