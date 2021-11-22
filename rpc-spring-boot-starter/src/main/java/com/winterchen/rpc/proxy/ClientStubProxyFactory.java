package com.winterchen.rpc.proxy;

import com.winterchen.core.discovery.DiscoveryService;
import com.winterchen.rpc.config.RpcClientProperties;

import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author winterchen
 * @version 1.0
 * @date 2021/11/17 11:19
 **/
public class ClientStubProxyFactory {

    private Map<Class<?>, Object> objectCache = new ConcurrentHashMap<>();

    /**
     * 获取代理对象
     *
     * @param clazz   接口
     * @param version 服务版本
     * @param <T>
     * @return 代理对象
     */
    public <T> T getProxy(Class<T> clazz, String version, DiscoveryService discoveryService, RpcClientProperties properties) {
        return (T) objectCache.computeIfAbsent(clazz, clz ->
                Proxy.newProxyInstance(clz.getClassLoader(), new Class[]{clz}, new ClientStubInvocationHandler(discoveryService, properties, clz, version))
        );
    }

}