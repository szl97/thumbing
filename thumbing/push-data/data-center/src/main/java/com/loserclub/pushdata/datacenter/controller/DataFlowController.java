package com.loserclub.pushdata.datacenter.controller;


import com.loserclub.pushdata.datacenter.service.DataFlowService;
import com.loserclub.shared.annotation.Authorize;
import com.loserclub.shared.auth.permission.PermissionConstants;
import com.loserclub.shared.controller.LoserClubBaseController;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
public class DataFlowController extends LoserClubBaseController {

    @Autowired
    private DataFlowService dataFlowService;

    @Authorize(PermissionConstants.REGISTER)
    @RequestMapping(value = "/balancedLoadServer", method = RequestMethod.GET)
    public String balancedLoadServer() {
        return dataFlowService.balancedLoadServer();
    }
}
