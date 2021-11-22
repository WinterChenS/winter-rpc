package com.winterchen.core.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @author winterchen
 * @version 1.0
 * @date 2021/11/16 13:26
 * @description 统一请求封装
 **/
@Data
public class RpcRequest implements Serializable {

    private static final long serialVersionUID = 8710532271944534528L;

    /**
     * 请求的服务名称 + 版本
     */
    private String serviceName;

    /**
     * 请求调用的方法
     */
    private String method;

    /**
     * 参数类型
     */
    private Class<?>[] parameterTypes;

    /**
     * 参数
     */
    private Object[] parameters;

}