package com.winterchen.core.exception;

/**
 * @author winterchen
 * @version 1.0
 * @date 2021/11/17 9:25
 **/
public class RpcException extends RuntimeException {

    private static final long serialVersionUID = 3365624081242234230L;

    public RpcException() {
        super();
    }

    public RpcException(String msg) {
        super(msg);
    }

    public RpcException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public RpcException(Throwable cause) {
        super(cause);
    }
}