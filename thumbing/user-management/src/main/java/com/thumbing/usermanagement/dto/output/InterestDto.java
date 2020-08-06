package com.thumbing.usermanagement.dto.output;

import com.thumbing.shared.dto.EntityDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/6 11:48
 */
@Data
public class InterestDto extends EntityDto {
    @ApiModelProperty(value = "兴趣")
    private String name;
    @Override
    public boolean equals(Object object){
        if(object == null) return false;
        if(!(object instanceof InterestDto)) return false;
        InterestDto a = (InterestDto) object;
        return  a.getId() == getId();
    }
}
