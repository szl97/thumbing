package com.thumbing.usermanagement.dto.input;

import com.thumbing.shared.dto.EntityDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Set;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/6 15:50
 */
@Data
public class PersonalEditInput extends EntityDto {
    @ApiModelProperty(value = "用户的用户名")
    private String userName;
    @ApiModelProperty(value = "星座")
    private String constellation;
    @ApiModelProperty(value = "是否是学生")
    private boolean is_student;
    @ApiModelProperty(value = "目前所在国家")
    private String currentCountry;
    /**
     * 兴趣
     */
    private Set<InterestInput> interests;
    /**
     * 专业领域
     */
    private OccupationInput occupation;
    /**
     * 职业
     */
    private JobInput job;
}
