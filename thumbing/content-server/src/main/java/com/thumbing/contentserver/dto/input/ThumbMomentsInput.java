package com.thumbing.contentserver.dto.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/26 14:27
 */
@Data
public class ThumbMomentsInput extends MomentsIdInput {
    @ApiModelProperty(value = "点赞或取消点赞")
    private boolean add;
}
