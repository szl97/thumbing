package com.loserclub.shared.entity.mongo.common;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Author: Stan Sai
 * @Date: 2020/7/18 13:09
 */
@Getter
@Setter
public class NickUser implements Serializable {
    private long userId;
    private String nickName;
    @Override
    public String toString(){
        return "userId:"+userId+", nickName:"+nickName;
    }
    @Override
    public boolean equals(Object object){
        if(object == null) return false;
        if(!(object instanceof NickUser)) return false;
        NickUser nickUser = (NickUser)object;
        return nickUser.userId == nickUser.userId;
    }
}
