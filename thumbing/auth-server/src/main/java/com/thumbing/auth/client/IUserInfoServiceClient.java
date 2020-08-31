package com.thumbing.auth.client;

import com.thumbing.shared.auth.model.UserContext;
import com.thumbing.shared.response.BaseApiResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/12 9:06
 */
@FeignClient(name = "user-management")
public interface IUserInfoServiceClient {
    @RequestMapping(value = "/personal/userInfo/register", method = RequestMethod.PUT)
    BaseApiResult<Boolean> createPersonal(UserContext userContext);
}
