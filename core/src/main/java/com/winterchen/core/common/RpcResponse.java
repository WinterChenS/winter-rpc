package com.winterchen.core.common;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author winterchen
 * @version 1.0
 * @date 2021/11/16 10:37
 * @description global result
 **/
@Builder
@Data
public class RpcResponse implements Serializable {

    private static final long serialVersionUID = -5452324343182848189L;

    private Integer code;

    private Object data;

    private String message;


    public RpcResponse() {
    }

    public RpcResponse(Integer code) {
        this.code = code;
    }

    public RpcResponse(Integer code, Object data) {
        this.code = code;
        this.data = data;
    }

    public RpcResponse(Integer code, Object data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public RpcResponse(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static RpcResponse ok() {
        return new RpcResponse(ResultCode.OK.getCode(), ResultCode.OK.getDesc());
    }

    public static RpcResponse ok(Object data) {
        return new RpcResponse(ResultCode.OK.getCode(), data, ResultCode.OK.getDesc());
    }

    public static RpcResponse notFound() {
        return new RpcResponse(ResultCode.NOT_FOUND.getCode(), ResultCode.NOT_FOUND.getDesc());
    }

    public static RpcResponse notFound(String message) {
        return new RpcResponse(ResultCode.NOT_FOUND.getCode(), message);
    }

    public static RpcResponse failed() {
        return new RpcResponse(ResultCode.ERROR.getCode(), ResultCode.ERROR.getDesc());

    }

    public static RpcResponse failed(Integer code, String message) {
        return new RpcResponse(code, message);
    }

}