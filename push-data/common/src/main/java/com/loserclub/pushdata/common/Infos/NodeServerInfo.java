package com.loserclub.pushdata.common.Infos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author Stan Sai
 * @date 2020-06-20
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NodeServerInfo {
    private String name;
    private String ip;
    private int devicePort;
    private int nodePort;
}
