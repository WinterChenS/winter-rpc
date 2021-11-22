package com.winterchen.rpc.proxy;

import com.winterchen.core.common.RpcRequest;
import com.winterchen.core.common.RpcResponse;
import com.winterchen.core.common.ServiceInfo;
import com.winterchen.core.common.ServiceUtil;
import com.winterchen.core.discovery.DiscoveryService;
import com.winterchen.core.exception.ResourceNotFoundException;
import com.winterchen.core.exception.RpcException;
import com.winterchen.core.protocol.MessageHeader;
import com.winterchen.core.protocol.MessageProtocol;
import com.winterchen.core.protocol.MsgStatus;
import com.winterchen.rpc.config.RpcClientProperties;
import com.winterchen.rpc.transport.NetClientTransportFactory;
import com.winterchen.rpc.transport.RequestMetadata;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author CENTURY
 * @version 1.0
 * @date 2021/11/17 11:21
 **/
@Slf4j
public class ClientStubInvocationHandler implements InvocationHandler {

    private DiscoveryService discoveryService;

    private RpcClientProperties properties;

    private Class<?> clazz;

    private String version;


    public ClientStubInvocationHandler(DiscoveryService discoveryService, RpcClientProperties properties, Class<?> clazz, String version) {
        this.discoveryService = discoveryService;
        this.properties = properties;
        this.clazz = clazz;
        this.version = version;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //获取服务信息
        ServiceInfo serviceInfo = discoveryService.discovery(ServiceUtil.serviceKey(this.clazz.getName(), this.version));
        if (serviceInfo == null) {
            throw new ResourceNotFoundException("404");
        }

        MessageProtocol<RpcRequest> rpcRequestMessageProtocol = new MessageProtocol<>();
        //设置请求头
        rpcRequestMessageProtocol.setHeader(MessageHeader.build(properties.getSerialization()));
        //设置请求体
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setMethod(method.getName());
        rpcRequest.setParameters(args);
        rpcRequest.setParameterTypes(method.getParameterTypes());
        rpcRequest.setServiceName(serviceInfo.getServiceName());
        rpcRequestMessageProtocol.setBody(rpcRequest);

        //发送网络请求，拿到结果
        MessageProtocol<RpcResponse> responseMessageProtocol = NetClientTransportFactory.getNetClientTransport()
                .sendRequest(RequestMetadata.builder().protocol(rpcRequestMessageProtocol).address(serviceInfo.getAddress())
                        .port(serviceInfo.getPort()).timeout(properties.getTimeout()).build());

        if (responseMessageProtocol == null) {
            log.error("请求超时");
            throw new RpcException("rpc调用结果失败， 请求超时 timeout:" + properties.getTimeout());
        }

        if (!MsgStatus.isSuccess(responseMessageProtocol.getHeader().getStatus())) {
            log.error("rpc调用结果失败， message：{}", responseMessageProtocol.getBody().getMessage());
            throw new RpcException(responseMessageProtocol.getBody().getMessage());
        }
        return responseMessageProtocol.getBody().getData();
    }
}