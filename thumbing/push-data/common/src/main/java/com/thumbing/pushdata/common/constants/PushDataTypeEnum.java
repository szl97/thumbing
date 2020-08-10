package com.thumbing.pushdata.common.constants;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/10 17:09
 */
public enum PushDataTypeEnum {
    RA("relationApply"),
    CP("commentPush");
    String type;
    PushDataTypeEnum(String type){
        this.type = type;
    }
    public String getType(){
        return type;
    }
}
