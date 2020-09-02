package com.thumbing.usermanagement.dto.output;

import com.thumbing.shared.dto.EntityDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/6 11:45
 */
@Data
public class PersonalDto extends EntityDto {
    @ApiModelProperty(value = "用户的用户名")
    private String userName;
    @ApiModelProperty(value = "昵称")
    private String nickName;
    @ApiModelProperty(value = "真实名字")
    private String name;
    @ApiModelProperty(value = "性别")
    private String gender;
    @ApiModelProperty(value = "出生日期")
    private LocalDateTime birthDate;
    @ApiModelProperty(value = "星座")
    private String constellation;
    @ApiModelProperty(value = "是否是学生")
    private boolean student;
    @ApiModelProperty(value = "目前所在国家")
    private String currentCountry;
    @ApiModelProperty(value = "故乡国家")
    private String nativeCountry;
    /**
     * 兴趣
     */
    private Set<InterestDto> interests;
    /**
     * 专业领域
     */
    private OccupationDto occupation;
    /**
     * 职业
     */
    private JobDto job;
}
