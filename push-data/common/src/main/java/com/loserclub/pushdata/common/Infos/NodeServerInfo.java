package com.loserclub.pushdata.common.Infos;


import lombok.Builder;
import lombok.Data;


/**
 * @author Stan Sai
 * @date 2020-06-20
 */
@Data
@Builder
public class NodeServerInfo {
    private String name;
    private String ip;
    private int devicePort;
    private int nodePort;
}
