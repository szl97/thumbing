package com.thumbing.contentserver.controller;

import com.thumbing.contentserver.dto.input.FetchRoastInput;
import com.thumbing.contentserver.dto.input.PublishRoastInput;
import com.thumbing.contentserver.dto.input.RoastIdInput;
import com.thumbing.contentserver.dto.input.ThumbRoastInput;
import com.thumbing.contentserver.dto.output.RoastDto;
import com.thumbing.contentserver.service.IRoastService;
import com.thumbing.shared.annotation.Authorize;
import com.thumbing.shared.annotation.EnableResponseAdvice;
import com.thumbing.shared.auth.permission.PermissionConstants;
import com.thumbing.shared.controller.ThumbingBaseController;
import com.thumbing.shared.dto.output.PageResultDto;
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
 * @Date: 2020/8/28 9:39
 */
@EnableResponseAdvice
@RestController
@RequestMapping(value = "/roast")
public class RoastController extends ThumbingBaseController {
    @Autowired
    private IRoastService roastService;

    @ApiOperation("发表心情吐槽")
    @Authorize(PermissionConstants.ACCESS)
    @RequestMapping(value = "publish", method = RequestMethod.PUT)
    public Boolean publishRoast(@RequestBody @Valid PublishRoastInput input){
        return roastService.publishRoast(input, getCurrentUser());
    }

    @ApiOperation("随机获取心情吐槽")
    @Authorize(PermissionConstants.REGISTER)
    @RequestMapping(value = "fetch", method = RequestMethod.GET)
    public List<RoastDto> fetchRoasts(){
        return roastService.fetchRoasts(getCurrentUser());
    }

    @ApiOperation("删除心情吐槽")
    @Authorize(PermissionConstants.ACCESS)
    @RequestMapping(value = "delete", method = RequestMethod.DELETE)
    public Boolean deleteRoast(RoastIdInput input) {
        return roastService.deleteRoast(input, getCurrentUser());
    }

    @ApiOperation("点赞")
    @Authorize(PermissionConstants.REGISTER)
    @RequestMapping(value = "thumb", method = RequestMethod.POST)
    public Boolean thumbRoast(@RequestBody @Valid ThumbRoastInput input){
        return roastService.thumbRoast(input, getCurrentUser());
    }

    @ApiOperation("获取自己发布的心情吐槽")
    @Authorize(PermissionConstants.ACCESS)
    @RequestMapping(value = "getMine", method = RequestMethod.GET)
    public PageResultDto<RoastDto> getMine(FetchRoastInput input){
        return roastService.getMine(input, getCurrentUser());
    }
}
