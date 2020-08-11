package com.thumbing.shared.entity.mongo.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Author: Stan Sai
 * @Date: 2020/7/18 13:09
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NickUser implements Serializable {
    private Long userId;
    private String nickName;
    /**
     * 用于判断chat session中的user是否显示这个session
     */
    private Boolean show;
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
