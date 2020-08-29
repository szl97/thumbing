package com.thumbing.contentserver.dto.output;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.thumbing.shared.dto.output.DocumentDto;
import com.thumbing.shared.entity.mongo.content.enums.ContentType;
import com.thumbing.shared.utils.serializer.LongToStringSerializer;
import com.thumbing.shared.utils.serializer.StringToLongDeserializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/27 10:19
 */
@Data
public class SearchOutput extends DocumentDto {
    @JsonSerialize(using = LongToStringSerializer.class)
    @JsonDeserialize(using = StringToLongDeserializer.class)
    @ApiModelProperty(value = "用户Id")
    private Long userId;
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "标签")
    private List<String> tagIds;
    @ApiModelProperty(value = "内容类型")
    private ContentType contentType;
    @ApiModelProperty(value = "内容")
    private String content;
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;
}
