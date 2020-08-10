package com.thumbing.shared.rabbit.message;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/10 15:20
 */
@Data
public class RelationApplyMsg implements Serializable {
    private Long toUserId;
    private Long fromUserId;
    private String fromUserName;
    private String remark;
}
