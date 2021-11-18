package com.winterchen.core.register;

import com.winterchen.core.common.ServiceInfo;

/**
 * @author CENTURY
 * @version 1.0
 * @date 2021/11/16 15:56
 * @description 服务注册
 **/
public interface RegistryService {

    void register(ServiceInfo serviceInfo) throws Exception;

    void unregister(ServiceInfo serviceInfo) throws Exception;

    void destroy() throws Exception;

}