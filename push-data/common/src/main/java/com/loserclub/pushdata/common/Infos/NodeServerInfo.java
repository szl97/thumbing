package com.loserclub.pushdata.common.Infos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.atomic.AtomicInteger;

@Data
@Builder
public class NodeServerInfo {
    private String name;
    private String ip;
    private int devicePort;
    private int nodePort;
}
