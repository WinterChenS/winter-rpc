package com.winterchen.rpc.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author CENTURY
 * @version 1.0
 * @date 2021/11/17 10:15
 * @description rpc客户端配置信息
 **/
@Data
public class RpcClientProperties {

    /**
     *  负载均衡
     */
    private String balance;

    /**
     *  序列化
     */
    private String serialization;

    /**
     *  服务发现地址
     */
    private String discoveryAddr;

    /**
     *  服务调用超时
     */
    private Integer timeout;

}