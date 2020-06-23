package com.loserclub.pushdata.common.exception;

import com.loserclub.pushdata.common.response.BaseApiResult;
import lombok.Getter;

/**
 * @author Stan Sai
 * @date 2020-06-23
 */
//业务异常类
@Getter
public class BusinessException extends RuntimeException{
    private static final long serialVersionUID = -502319800902002585L;

    private BaseApiResult result;

    public BusinessException() {
        result=BaseApiResult.errorServer("服务端异常");
    }

    public BusinessException(String message) {
        result=BaseApiResult.errorServer(message);
    }

    public BusinessException(BaseApiResult result){
        this.result=result;
    }
}
