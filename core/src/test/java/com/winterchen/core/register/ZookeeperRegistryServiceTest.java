package com.winterchen.core.register;

import com.winterchen.core.balancer.FullRoundBalance;
import com.winterchen.core.common.ServiceInfo;
import com.winterchen.core.discovery.ZookeeperDiscoveryService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ZookeeperRegistryServiceTest {

    private static final String ZOOKEEPER_ADDRESS = "127.0.0.1:2181";

    @SneakyThrows
    @Test
    public void testRegistry() {

        ZookeeperRegistryService service = new ZookeeperRegistryService(ZOOKEEPER_ADDRESS);
        assertNotNull(service);
        ServiceInfo serviceInfo = new ServiceInfo();
        serviceInfo.setServiceName("test");
        serviceInfo.setAddress("127.0.0.1");
        serviceInfo.setPort(8081);
        serviceInfo.setAppName("testApp");
        serviceInfo.setVersion("1.0");
        service.register(serviceInfo);
        ZookeeperDiscoveryService discoveryService = new ZookeeperDiscoveryService(ZOOKEEPER_ADDRESS, new FullRoundBalance());
        ServiceInfo discovery = discoveryService.discovery(serviceInfo.getServiceName());
        assertEquals(discovery, serviceInfo);
        service.destroy();
    }


}