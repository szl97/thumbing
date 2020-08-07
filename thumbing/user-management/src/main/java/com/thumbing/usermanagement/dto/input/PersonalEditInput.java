package com.thumbing.usermanagement.dto.input;

import com.thumbing.shared.dto.EntityDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/6 15:50
 */
@Data
public class PersonalEditInput extends EntityDto {
    @ApiModelProperty(value = "昵称")
    private String nickName;
    @ApiModelProperty(value = "星座")
    private String constellation;
    @ApiModelProperty(value = "是否是学生")
    private boolean student;
    @ApiModelProperty(value = "目前所在国家")
    private String currentCountry;
    /**
     * 兴趣
     */
    private List<InterestInput> interests;
    /**
     * 专业领域
     */
    private OccupationInput occupation;
    /**
     * 职业
     */
    private JobInput job;
}
