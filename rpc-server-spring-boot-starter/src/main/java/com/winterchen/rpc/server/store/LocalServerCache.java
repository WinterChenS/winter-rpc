package com.winterchen.rpc.server.store;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author CENTURY
 * @version 1.0
 * @date 2021/11/17 11:01
 * @description 将暴露的服务缓存到本地
 **/
public class LocalServerCache {

    private static final Map<String, Object> serverCacheMap = new ConcurrentHashMap<>();

    public static void store(String serverName, Object server) {
        serverCacheMap.merge(serverName, server, (Object oldObj, Object newObj) -> newObj);
    }

    public static Object get(String serverName) {
        return serverCacheMap.get(serverName);
    }

    public static Map<String, Object> getAll(){
        return null;
    }

}