package com.winterchen.rpc.config;

import lombok.Data;

/**
 * @author winterchen
 * @version 1.0
 * @date 2021/11/17 10:14
 * @description rpc 服务端配置
 **/
@Data
public class RpcServerProperties {

    /**
     * 注册中心类型: zookeeper/nacos
     */
    private String type;

    private Integer port;

    private String appName;

    private String registryAddr;

}