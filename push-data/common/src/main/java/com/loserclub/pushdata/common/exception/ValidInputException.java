package com.loserclub.pushdata.common.exception;

/**
 * @author Stan Sai
 * @date 2020-06-23
 */
public class ValidInputException extends RuntimeException {
    private static final long serialVersionUID = -6758570625365953188L;

    public ValidInputException() {
        super("客户端输入异常");
    }

    public ValidInputException(String message) {
        super(message);
    }
}
