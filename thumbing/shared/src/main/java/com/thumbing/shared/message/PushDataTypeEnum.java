package com.thumbing.shared.message;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/10 17:09
 */
public enum PushDataTypeEnum {
    RA("relationApply"),
    AC("articleComment"),
    AT("articleThumbing"),
    MC("momentComment"),
    MT("momentThumbing"),
    RT("roastThumbing"),
    CT("commentThumbing");
    String type;
    PushDataTypeEnum(String type){
        this.type = type;
    }
    public String getType(){
        return type;
    }
}
