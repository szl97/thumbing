package com.loserclub.pushdata.datacenter.controller;


import com.loserclub.pushdata.datacenter.service.DataFlowService;
import com.loserclub.shared.controller.LoserClubBaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Stan Sai
 * @date 2020-06-23
 */
@RestController
@RequestMapping("/dataFlow")
public class DataFlowController extends LoserClubBaseController {

    @Autowired
    private DataFlowService dataFlowService;

    @GetMapping("/balancedLoadServer")
    public String balancedLoadServer() {
        return dataFlowService.balancedLoadServer();
    }
}
