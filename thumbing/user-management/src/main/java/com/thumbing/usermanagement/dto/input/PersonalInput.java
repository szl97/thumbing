package com.thumbing.usermanagement.dto.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/6 13:48
 */
@Data
public class PersonalInput implements Serializable {
    @ApiModelProperty(value = "昵称")
    private String nickName;
    @ApiModelProperty(value = "真实名字")
    private String name;
    @ApiModelProperty(value = "性别")
    private String gender;
    @ApiModelProperty(value = "出生日期")
    private LocalDate birthDate;
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
