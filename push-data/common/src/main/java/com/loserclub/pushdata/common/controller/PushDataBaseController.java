package com.loserclub.pushdata.common.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.loserclub.pushdata.common.exception.BusinessException;
import com.loserclub.pushdata.common.exception.ValidInputException;
import com.loserclub.pushdata.common.response.BaseApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author Stan Sai
 * @date 2020-06-23
 */
public class PushDataBaseController extends BaseController {

    @Autowired
    private ObjectMapper mapper;

    // 客户端参数异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public void  handleException(MethodArgumentNotValidException e, HttpServletResponse response) throws IOException {
        e.printStackTrace();
        BindingResult result = e.getBindingResult();
        StringBuffer sb = new StringBuffer();
        for (ObjectError error : result.getAllErrors()) {
            sb.append(error.getDefaultMessage() + " ");
        }
        String error = sb == null ? "" : sb.toString().trim();
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        mapper.writeValue(response.getWriter(), BaseApiResult.errorServer("客户端参数异常[" + error + "]"));
    }

    //客户端输入异常
    @ExceptionHandler(ValidInputException.class)
    @ResponseBody
    public void handleException(ValidInputException e, HttpServletResponse response) throws IOException {
        e.printStackTrace();
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        mapper.writeValue(response.getWriter(), BaseApiResult.errorServer(e.getMessage()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public void handleException(HttpMessageNotReadableException e, HttpServletResponse response) throws IOException {
        e.printStackTrace();
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        mapper.writeValue(response.getWriter(), BaseApiResult.errorServer("请求内容缺失，请检查"));
    }

    //服务端主动抛出异常，通常是业务逻辑校验异常
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public BaseApiResult handleException(BusinessException e) {
        e.printStackTrace();
        return e.getResult();
    }

    //服务端执行异常 直接返回500错误
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public BaseApiResult handleException(Exception e, HttpServletResponse response) {
        e.printStackTrace();
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return BaseApiResult.errorServer("服务端异常:" + e.getMessage() + "");
    }
}
