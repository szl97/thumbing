package com.thumbing.contentserver.dto.output;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.thumbing.shared.dto.output.DocumentDto;
import com.thumbing.shared.utils.serializer.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * @author Stan Sai
 * @date 2020-08-19 9:40
 */
@Data
public class ArticleDto extends DocumentDto {
    @JsonSerialize(using = LongToStringSerializer.class)
    @JsonDeserialize(using = StringToLongDeserializer.class)
    @ApiModelProperty(value = "用户Id")
    private Long userId;
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "标签")
    private Set<String> tagIds;
    @ApiModelProperty(value = "简述（前100字）")
    private String abstracts;
    @ApiModelProperty(value = "内容")
    private String content;
    @JsonSerialize(using = LongToStringSetSerializer.class)
    @JsonDeserialize(using = StringToLongSetDeserializer.class)
    @ApiModelProperty(value = "点赞用户")
    private Set<Long> thumbUserIds;
    @ApiModelProperty(value = "点赞数")
    private Integer thumbingNum;
    @ApiModelProperty(value = "评论数")
    private Integer commentsNum;
    @ApiModelProperty(value = "文章中的图片在OSS中的标识")
    private List<String> graphIds;
}
