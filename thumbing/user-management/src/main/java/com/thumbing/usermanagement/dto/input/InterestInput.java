package com.thumbing.usermanagement.dto.input;

import com.thumbing.shared.dto.EntityDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/6 11:48
 */
@Data
public class InterestInput extends EntityDto {
    @ApiModelProperty(value = "兴趣")
    private String name;
    @Override
    public boolean equals(Object object){
        if(object == null) return false;
        if(!(object instanceof InterestInput)) return false;
        InterestInput a = (InterestInput) object;
        return  a.getId() == getId();
    }
}
