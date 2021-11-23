package com.winterchen.core.register;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.winterchen.core.common.ServiceInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * @author CENTURY
 * @version 1.0
 * @date 2021/11/23 14:51
 * @description nacos注册中心
 **/
@Slf4j
public class NacosRegistryService implements RegistryService {

    private NamingService namingService;

    public NacosRegistryService(String registryAddress) {
        try {
            this.namingService = NamingFactory.createNamingService(registryAddress);
        } catch (Exception e) {
            log.error("nacos discovery connection error: {}", registryAddress);
        }
    }

    @Override
    public void register(ServiceInfo serviceInfo) throws Exception {
        namingService.registerInstance(serviceInfo.getServiceName(), toInstance(serviceInfo));
    }

    @Override
    public void unregister(ServiceInfo serviceInfo) throws Exception {
        namingService.deregisterInstance(serviceInfo.getServiceName(), toInstance(serviceInfo));
    }

    @Override
    public void destroy() throws Exception {
        namingService.shutDown();
    }

    private Instance toInstance(ServiceInfo serviceInfo) {
        Instance instance = new Instance();
        instance.setPort(serviceInfo.getPort());
        instance.setIp(serviceInfo.getAddress());
        instance.setServiceName(serviceInfo.getServiceName());
        instance.addMetadata("payload", JSON.toJSONString(serviceInfo));
        return instance;
    }

}