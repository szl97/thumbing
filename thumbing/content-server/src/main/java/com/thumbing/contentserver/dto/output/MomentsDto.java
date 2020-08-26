package com.thumbing.contentserver.dto.output;

import com.thumbing.shared.dto.output.DocumentDto;
import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/18 17:27
 */
@Data
public class MomentsDto extends DocumentDto {
    /**
     * 用户id
     */
    private long userId;
    /**
     * 标题
     */
    private String title;
    /**
     * 标签
     */
    private Set<Long> tagIds;
    /**
     * 内容
     */
    private String content;
    /**
     * 点赞数
     */
    private Integer thumbingNum;
    /**
     * 评论数
     */
    private Integer commentsNum;
    /**
     * 点赞用户
     */
    private Set<Long> thumbUserIds;
    /**
     * 下一个评论用户显示的昵称
     */
    private int nickNameSequence;
    /**
     * 动态中的图片在OSS中的标识
     */
    private List<String> graphIds;
}
