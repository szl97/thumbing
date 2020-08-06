package com.thumbing.usermanagement.dto.output;

import com.thumbing.shared.dto.EntityDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/6 11:49
 */
@Data
public class OccupationDto extends EntityDto {
    @ApiModelProperty(value = "专业领域")
    private String name;
}
