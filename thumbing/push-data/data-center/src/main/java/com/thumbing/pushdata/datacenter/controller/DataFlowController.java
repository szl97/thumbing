package com.thumbing.pushdata.datacenter.controller;


import com.thumbing.pushdata.datacenter.service.DataFlowService;
import com.thumbing.shared.annotation.Authorize;
import com.thumbing.shared.auth.permission.PermissionConstants;
import com.thumbing.shared.controller.ThumbingBaseController;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Stan Sai
 * @date 2020-06-23
 */
@RestController
@RequestMapping("/dataFlow")
@Api(tags = "消息中心")
public class DataFlowController extends ThumbingBaseController {

    @Autowired
    private DataFlowService dataFlowService;

    @Authorize(PermissionConstants.REGISTER)
    @RequestMapping(value = "/balancedLoadServer", method = RequestMethod.GET)
    public String balancedLoadServer() {
        return dataFlowService.balancedLoadServer();
    }
}
