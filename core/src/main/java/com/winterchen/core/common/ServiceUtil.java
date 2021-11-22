package com.winterchen.core.common;

/**
 * @author winterchen
 * @version 1.0
 * @date 2021/11/16 13:38
 * @description 服务工具类
 **/
public class ServiceUtil {

    public static String serviceKey(String serviceName, String version) {
        return String.join("-", serviceName, version);
    }

}