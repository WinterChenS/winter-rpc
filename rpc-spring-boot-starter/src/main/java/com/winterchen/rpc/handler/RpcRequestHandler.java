package com.winterchen.rpc.handler;

import com.winterchen.core.common.RpcRequest;
import com.winterchen.core.common.RpcResponse;
import com.winterchen.core.protocol.MessageHeader;
import com.winterchen.core.protocol.MessageProtocol;
import com.winterchen.core.protocol.MsgStatus;
import com.winterchen.core.protocol.MsgType;
import com.winterchen.rpc.store.LocalServerCache;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author CENTURY
 * @version 1.0
 * @date 2021/11/17 10:47
 **/
@Slf4j
public class RpcRequestHandler extends SimpleChannelInboundHandler<MessageProtocol<RpcRequest>> {

    private final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 10, 60L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10000));


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageProtocol<RpcRequest> rpcRequestMessageProtocol) throws Exception {
        threadPoolExecutor.submit(() -> {
            MessageProtocol<RpcResponse> resProtocol = new MessageProtocol<>();
            RpcResponse response = new RpcResponse();
            MessageHeader header = rpcRequestMessageProtocol.getHeader();
            header.setMsgType(MsgType.RESPONSE.getType());
            try {
                Object result = handle(rpcRequestMessageProtocol.getBody());
                response.setData(result);
                header.setStatus(MsgStatus.SUCCESS.getCode());
                resProtocol.setHeader(header);
                resProtocol.setBody(response);
            } catch (Throwable throwable) {
                header.setStatus(MsgStatus.FAIL.getCode());
                response.setMessage(throwable.toString());
                log.error("process request {} error", header.getRequestId(), throwable);
            }
            //把数据写回去
            channelHandlerContext.writeAndFlush(resProtocol);
        });
    }

    private Object handle(RpcRequest rpcRequest) {
        try {
            Object bean = LocalServerCache.get(rpcRequest.getServiceName());
            if (bean == null) {
                throw new RuntimeException(String.format("service not exist: %s !", rpcRequest.getServiceName()));
            }
            // 反射调用
            Method method = bean.getClass().getMethod(rpcRequest.getMethod(), rpcRequest.getParameterTypes());
            return method.invoke(bean, rpcRequest.getParameters());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}