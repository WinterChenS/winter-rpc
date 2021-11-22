package com.winterchen.core.discovery;

import com.winterchen.core.common.ServiceInfo;

/**
 * @author winterchen
 * @version 1.0
 * @date 2021/11/16 16:08
 * @description 服务发现
 **/
public interface DiscoveryService {

    ServiceInfo discovery(String serviceName) throws Exception;

}