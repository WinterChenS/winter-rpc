package com.winterchen.rpc.store;

import com.winterchen.core.common.RpcResponse;
import com.winterchen.core.protocol.MessageProtocol;
import com.winterchen.rpc.transport.client.RpcFuture;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author winterchen
 * @version 1.0
 * @date 2021/11/17 11:11
 * @description 请求和响应映射对象
 **/
public class LocalRpcResponseCache {

    private static Map<String, RpcFuture<MessageProtocol<RpcResponse>>> requestResponseCache = new ConcurrentHashMap<>();


    /**
     *  添加请求和响应的映射关系
     * @param reqId
     * @param future
     */
    public static void add(String reqId, RpcFuture<MessageProtocol<RpcResponse>> future){
        requestResponseCache.put(reqId, future);
    }

    /**
     *  设置响应数据
     * @param reqId
     * @param messageProtocol
     */
    public static void fillResponse(String reqId, MessageProtocol<RpcResponse> messageProtocol){
        // 获取缓存中的 future
        RpcFuture<MessageProtocol<RpcResponse>> future = requestResponseCache.get(reqId);

        // 设置数据
        future.setResponse(messageProtocol);

        // 移除缓存
        requestResponseCache.remove(reqId);
    }

}