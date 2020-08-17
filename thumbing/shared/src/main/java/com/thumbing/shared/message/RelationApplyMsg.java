package com.thumbing.shared.message;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/10 15:20
 */
@Data
public class RelationApplyMsg implements Serializable {
    private Long dataId;
    private Long toUserId;
    private Long fromUserId;
    private String fromUserName;
    private String remark;
    private LocalDateTime time;
}
