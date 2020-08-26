package com.thumbing.contentserver.dto.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/26 16:28
 */
@Data
public class ThumbCommentInput extends CommentIdInput {
    @ApiModelProperty(value = "点赞或取消点赞")
    private boolean add;
}
