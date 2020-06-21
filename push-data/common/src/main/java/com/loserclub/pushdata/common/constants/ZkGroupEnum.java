package com.loserclub.pushdata.common.constants;

import lombok.Getter;

@Getter
public enum ZkGroupEnum {
    NODE_SERVER("/NODE-SERVER"),
    DATA_CENTER("/DATA-CENTER");
    private String value;

    ZkGroupEnum(String value) {
        this.value = value;
    }
}
