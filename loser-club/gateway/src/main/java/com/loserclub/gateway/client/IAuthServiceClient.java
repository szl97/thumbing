package com.loserclub.gateway.client;

import com.loserclub.gateway.feign.FeignConfig;
import com.loserclub.shared.response.BaseApiResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: Stan Sai
 * @Date: 2020/7/14 13:48
 */
@FeignClient(name = "auth-server", configuration = FeignConfig.class)
public interface IAuthServiceClient {

    @RequestMapping(value = "/authorization/checkAuthorization", method = RequestMethod.GET)
    BaseApiResult<Boolean> auth(@RequestParam("authorization") String authorization,
                       @RequestParam("applicationName") String applicationName,
                       @RequestParam("url") String url);
}
