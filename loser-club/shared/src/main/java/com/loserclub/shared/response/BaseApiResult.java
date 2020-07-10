package com.loserclub.shared.response;

import java.io.Serializable;

public class BaseApiResult<T extends Object> implements Serializable {
    private static int CODE_SUCCESS = 0;//默认值
    public static int CODE_ERROR_SERVER = 1; //服务端错误 与前端约定好的 1是不允许使用的code

    public static String MESSAGE_ERROR_INVALID = "非法请求"; //验证失败
    public static String MESSAGE_ERROR_CLIENT = "请检查必填项是否为空"; // 客户端错误
    public static String MESSAGE_ERROR_SERVER = "操作失败，未知异常"; //服务端错误
    public static String MESSAGE_SUCCESS = "操作成功";

    /**
     * 提示编码
     */
    private int code;
    /**
     * 描述
     */
    private String message;
    /**
     * 数据
     */
    private T data;

    private boolean success;

    public BaseApiResult() {
    }

    public BaseApiResult(int code, String msg, T data, boolean success) {
        this.code = code;
        this.message = msg;
        this.data = data;
        this.success = success;
    }


    //region success
    public static BaseApiResult success() {
        return success(MESSAGE_SUCCESS);
    }

    public static BaseApiResult success(String msg) {
        return success(null, msg);
    }

    public static <T> BaseApiResult success(T data) {
        return success(data, MESSAGE_SUCCESS);
    }

    public static <T> BaseApiResult success(T data, String msg) {
        return new BaseApiResult<>(CODE_SUCCESS, msg, data, true);
    }
    // endregion

    //region error server
    public static BaseApiResult errorServer() {
        return errorServer(MESSAGE_ERROR_SERVER);
    }

    public static BaseApiResult errorServer(String msg) {
        return errorServer(msg, null);
    }

    public static <T> BaseApiResult errorServer(String msg, T data) {
        return new BaseApiResult<>(CODE_ERROR_SERVER, msg, data, false);
    }
    //endregion


}
