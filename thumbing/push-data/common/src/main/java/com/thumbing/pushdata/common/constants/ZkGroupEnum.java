package com.thumbing.pushdata.common.constants;

import lombok.Getter;

/**
 * @author Stan Sai
 * @date 2020-06-20
 */
@Getter
public enum ZkGroupEnum {
    NODE_SERVER("/NODE-SERVER"),
    DATA_CENTER("/DATA-CENTER");
    private String value;

    ZkGroupEnum(String value) {
        this.value = value;
    }
}
