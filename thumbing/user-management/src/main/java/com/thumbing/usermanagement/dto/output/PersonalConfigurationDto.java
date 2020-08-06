package com.thumbing.usermanagement.dto.output;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/6 16:10
 */
@Data
public class PersonalConfigurationDto implements Serializable {
    private List<InterestDto> interests;
    private List<JobDto> jobs;
    private List<OccupationDto> occupations;
}
