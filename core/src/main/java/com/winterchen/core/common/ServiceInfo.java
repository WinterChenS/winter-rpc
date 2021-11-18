package com.winterchen.core.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @author CENTURY
 * @version 1.0
 * @date 2021/11/16 13:37
 * @description 服务信息
 **/
@Data
public class ServiceInfo implements Serializable {

    private static final long serialVersionUID = -7638269198797513679L;

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * 版本
     */
    private String version;

    /**
     * 地址
     */
    private String address;

    /**
     * 端口
     */
    private Integer port;

}