package com.winterchen.core.discovery;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.winterchen.core.common.ServiceInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * @author CENTURY
 * @version 1.0
 * @date 2021/11/23 15:10
 * @description nacos服务发现
 **/
@Slf4j
public class NacosDiscoveryService implements DiscoveryService {

    private NamingService namingService;

    public NacosDiscoveryService(String registryAddress) {
        try {
            this.namingService = NamingFactory.createNamingService(registryAddress);
        } catch (Exception e) {
            log.error("nacos discovery connection error: {}", registryAddress);
        }
    }

    @Override
    public ServiceInfo discovery(String serviceName) throws Exception {
        //从nacos随机获取一个健康的实例
        Instance instance = namingService.selectOneHealthyInstance(serviceName);
        if (instance != null) {
            return JSON.parseObject(instance.getMetadata().get("payload"), ServiceInfo.class);
        }
        return null;
    }

}