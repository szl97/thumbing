package com.thumbing.contentserver.dto.output;

import com.thumbing.shared.dto.output.DocumentDto;
import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * @author Stan Sai
 * @date 2020-08-19 9:40
 */
@Data
public class ArticleDto extends DocumentDto {
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 标题
     */
    private String title;
    /**
     * 标签
     */
    private Set<Long> tagIds;
    /**
     * 前100字
     */
    private String abstracts;
    /**
     * 内容
     */
    private String content;
    /**
     * 点赞用户
     */
    private Set<Long> thumbUserIds;
    /**
     * 点赞数
     */
    private Integer thumbingNum;
    /**
     * 评论数
     */
    private Integer commentsNum;
    /**
     * 文章中的图片在OSS中的标识
     */
    private List<String> graphIds;
    /**
     * 浏览过的用户id
     */
    private Set<Long> browseUserIds;
}
