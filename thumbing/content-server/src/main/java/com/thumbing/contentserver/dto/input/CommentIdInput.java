package com.thumbing.contentserver.dto.input;

import com.thumbing.shared.dto.EntityDto;
import com.thumbing.shared.entity.mongo.content.enums.ContentType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author Stan Sai
 * @date 2020-08-26 5:00
 */
@Data
public class CommentIdInput extends EntityDto {
    @ApiModelProperty(value = "文章或帖子Id")
    @NotNull(message = "文章或帖子Id不可为空")
    private String contentId;
    @ApiModelProperty(value = "内容类型")
    @NotNull(message = "内容类型不可为空")
    private ContentType contentType;
}
