package com.thumbing.usermanagement.dto.output;

import com.thumbing.shared.dto.EntityDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/6 11:48
 */
@Data
public class JobDto extends EntityDto {
    @ApiModelProperty(value = "工作")
    private String name;
}
