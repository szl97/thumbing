package com.thumbing.usermanagement.dto.output;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.thumbing.shared.dto.EntityDto;
import com.thumbing.shared.utils.serializer.LongToStringSerializer;
import com.thumbing.shared.utils.serializer.StringToLongDeserializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/7 13:26
 */
@Data
public class BlackListDto extends EntityDto {
    @ApiModelProperty(value = "用户Id")
    @JsonSerialize(using = LongToStringSerializer.class)
    @JsonDeserialize(using = StringToLongDeserializer.class)
    private Long userId;
    @ApiModelProperty(value = "用户名")
    private String userName;
    @ApiModelProperty(value = "昵称")
    private String nickName;
}
