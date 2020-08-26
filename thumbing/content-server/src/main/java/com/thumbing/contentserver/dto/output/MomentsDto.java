package com.thumbing.contentserver.dto.output;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.thumbing.shared.dto.output.DocumentDto;
import com.thumbing.shared.utils.serializer.LongToStringSerializer;
import com.thumbing.shared.utils.serializer.LongToStringSetSerializer;
import com.thumbing.shared.utils.serializer.StringToLongDeserializer;
import com.thumbing.shared.utils.serializer.StringToLongSetDeserializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Set;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/18 17:27
 */
@Data
public class MomentsDto extends DocumentDto {
    @JsonSerialize(using = LongToStringSerializer.class)
    @JsonDeserialize(using = StringToLongDeserializer.class)
    @ApiModelProperty(value = "用户Id")
    private Long userId;
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "标签")
    private Set<String> tagIds;
    @ApiModelProperty(value = "内容")
    private String content;
    @ApiModelProperty(value = "点赞数")
    private Integer thumbingNum;
    @ApiModelProperty(value = "评论数")
    private Integer commentsNum;
    @JsonSerialize(using = LongToStringSetSerializer.class)
    @JsonDeserialize(using = StringToLongSetDeserializer.class)
    @ApiModelProperty(value = "点赞用户")
    private Set<Long> thumbUserIds;
}
