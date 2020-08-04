package com.thumbing.auth.dto.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/4 12:58
 */
@Data
public class DeviceInput implements Serializable {
    @ApiModelProperty(value = "设备ID")
    private String deviceId;
    @ApiModelProperty(value = "设备名")
    private String deviceName;
    @ApiModelProperty(value = "设备信息")
    private String deviceInfo;
}
