package com.winterchen.rpc.handler;

import com.winterchen.core.common.RpcResponse;
import com.winterchen.core.protocol.MessageProtocol;
import com.winterchen.rpc.store.LocalRpcResponseCache;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author winterchen
 * @version 1.0
 * @date 2021/11/17 11:16
 * @description rpc响应处理类
 **/
public class RpcResponseHandler extends SimpleChannelInboundHandler<MessageProtocol<RpcResponse>> {


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageProtocol<RpcResponse> rpcResponseMessageProtocol) throws Exception {
        String requestId = rpcResponseMessageProtocol.getHeader().getRequestId();
        LocalRpcResponseCache.fillResponse(requestId, rpcResponseMessageProtocol);
    }
}