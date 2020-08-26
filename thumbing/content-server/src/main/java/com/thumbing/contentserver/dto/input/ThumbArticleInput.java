package com.thumbing.contentserver.dto.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/24 16:35
 */
@Data
public class ThumbArticleInput extends ArticleIdInput {
    @ApiModelProperty(value = "点赞或取消点赞")
    private boolean add;
}
