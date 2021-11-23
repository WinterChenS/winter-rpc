package com.winterchen.core.discovery;

import com.winterchen.core.common.ServiceInfo;
import com.winterchen.core.register.NacosRegistryService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NacosDiscoveryServiceTest {

    private static final String NACOS_ADDRESS = "127.0.0.1:8848";

    @Test
    public void testDiscovery() {
        NacosRegistryService nacosRegistryService = new NacosRegistryService(NACOS_ADDRESS);
        ServiceInfo serviceInfo = new ServiceInfo();
        serviceInfo.setServiceName("test");
        serviceInfo.setAddress("127.0.0.1");
        serviceInfo.setPort(8081);
        serviceInfo.setAppName("testApp");
        serviceInfo.setVersion("1.0");
        try {
            nacosRegistryService.register(serviceInfo);
            NacosDiscoveryService nacosDiscoveryService = new NacosDiscoveryService(NACOS_ADDRESS);
            ServiceInfo discovery = nacosDiscoveryService.discovery(serviceInfo.getServiceName());
            assertEquals(discovery, serviceInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}