package com.thumbing.pushdata.common.Infos;


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
public class NodeServerInfo extends BaseAppInfo {
    private String name;
    private String ip;
    private int port;
    private int messagePort;
    private int devicePort;
}
