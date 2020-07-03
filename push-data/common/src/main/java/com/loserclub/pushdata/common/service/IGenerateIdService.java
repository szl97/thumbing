package com.loserclub.pushdata.common.service;
import com.loserclub.pushdata.common.jpa.GenerateIdResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * @author Stan Sai
 * @date 2020-06-28
 */
@FeignClient(name = "user-provider",path = "/generateId")
public interface IGenerateIdService {
    @GetMapping
    GenerateIdResult GenerateId();
}
