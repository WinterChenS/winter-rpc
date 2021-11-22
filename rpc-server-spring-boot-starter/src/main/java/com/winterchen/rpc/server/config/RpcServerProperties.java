package com.winterchen.rpc.server.config;

import lombok.Data;

/**
 * @author CENTURY
 * @version 1.0
 * @date 2021/11/17 10:14
 * @description rpc 服务端配置
 **/
@Data
public class RpcServerProperties {

    private Integer port;

    private String appName;

    private String registryAddr;

}