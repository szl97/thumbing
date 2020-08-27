package com.thumbing.contentserver.dto.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/24 16:37
 */
@Data
public class UpdateArticleInput extends ArticleIdInput {
    @ApiModelProperty(value = "内容")
    @NotNull(message = "内容不可为空")
    @Size(max = 10000, min = 500, message = "内容长度限制500-10000")
    private String content;
}
