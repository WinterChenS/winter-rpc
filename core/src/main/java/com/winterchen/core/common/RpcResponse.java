package com.winterchen.core.common;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author CENTURY
 * @version 1.0
 * @date 2021/11/16 10:37
 * @description global result
 **/
@Builder
@Data
public class RpcResponse<T> implements Serializable {

    private static final long serialVersionUID = -5452324343182848189L;

    private Integer code;

    private T data;

    private String message;


    public RpcResponse() {
    }

    public RpcResponse(Integer code) {
        this.code = code;
    }

    public RpcResponse(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public RpcResponse(Integer code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public RpcResponse(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static <T> RpcResponse<T> ok() {
        return new RpcResponse<>(ResultCode.OK.getCode(), ResultCode.OK.getDesc());
    }

    public static <T> RpcResponse<T> ok(T data) {
        return new RpcResponse<>(ResultCode.OK.getCode(), data, ResultCode.OK.getDesc());
    }

    public static <T> RpcResponse<T> notFound() {
        return new RpcResponse<>(ResultCode.NOT_FOUND.getCode(), ResultCode.NOT_FOUND.getDesc());
    }

    public static <T> RpcResponse<T> notFound(String message) {
        return new RpcResponse<>(ResultCode.NOT_FOUND.getCode(), message);
    }

    public static <T> RpcResponse<T> failed() {
        return new RpcResponse<>(ResultCode.ERROR.getCode(), ResultCode.ERROR.getDesc());

    }

    public static <T> RpcResponse<T> failed(Integer code, String message) {
        return new RpcResponse<>(code, message);
    }

}