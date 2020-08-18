package com.thumbing.recordserver.controller;

import com.thumbing.recordserver.dto.input.ReadPushDataRecord;
import com.thumbing.recordserver.dto.output.PushDataDto;
import com.thumbing.recordserver.service.IPushDataService;
import com.thumbing.shared.annotation.Authorize;
import com.thumbing.shared.annotation.EnableResponseAdvice;
import com.thumbing.shared.auth.permission.PermissionConstants;
import com.thumbing.shared.controller.ThumbingBaseController;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/18 10:53
 */
@EnableResponseAdvice
@RestController
@RequestMapping("/data")
public class PushDataController extends ThumbingBaseController {
    @Autowired
    private IPushDataService dataService;

    @ApiOperation("获取所有推送消息")
    @RequestMapping(method = RequestMethod.GET)
    @Authorize(PermissionConstants.REGISTER)
    public List<PushDataDto> fetchAllPushDataRecords(){
        return dataService.fetchAllPushDataRecords(getCurrentUser());
    }

    @ApiOperation("推送消息已读")
    @RequestMapping(method = RequestMethod.POST)
    @Authorize(PermissionConstants.REGISTER)
    public Boolean readRecord(@RequestBody @Valid ReadPushDataRecord input){
        return dataService.readRecord(input, getCurrentUser());
    }
}
