package com.winterchen.rpc.transport.client;

import com.winterchen.core.codec.RpcDecoder;
import com.winterchen.core.codec.RpcEncoder;
import com.winterchen.core.common.RpcRequest;
import com.winterchen.core.common.RpcResponse;
import com.winterchen.core.protocol.MessageProtocol;
import com.winterchen.rpc.handler.RpcResponseHandler;
import com.winterchen.rpc.store.LocalRpcResponseCache;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author winterchen
 * @version 1.0
 * @date 2021/11/17 13:11
 **/
@Slf4j
public class NettyNetClientTransport implements NetClientTransport {

    private final EventLoopGroup eventLoopGroup;

    private final Bootstrap bootstrap;

    private final RpcResponseHandler rpcResponseHandler;

    public NettyNetClientTransport() {
        this.eventLoopGroup = new NioEventLoopGroup(4);
        this.bootstrap = new Bootstrap();
        this.rpcResponseHandler = new RpcResponseHandler();

        this.bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline()
                                //解码 入站操作 将二进制解码成消息
                                .addLast(new RpcDecoder())
                                //接收响应 入站操作
                                .addLast(rpcResponseHandler)
                                //编码 是出站操作 将消息编码成二进制
                                .addLast(new RpcEncoder<>());
                    }
                });
    }

    @Override
    public MessageProtocol<RpcResponse> sendRequest(RequestMetadata metadata) throws Exception {
        MessageProtocol<RpcRequest> protocol = metadata.getProtocol();
        RpcFuture<MessageProtocol<RpcResponse>> future = new RpcFuture<>();
        LocalRpcResponseCache.add(protocol.getHeader().getRequestId(), future);

        //tcp连接
        ChannelFuture channelFuture = bootstrap.connect(metadata.getAddress(), metadata.getPort()).sync();
        channelFuture.addListener((ChannelFutureListener) arg0 -> {
            if (channelFuture.isSuccess()) {
                log.info("connect rpc server {} on port {} success.", metadata.getAddress(), metadata.getPort());
            } else {
                log.error("connect rpc server {} on port {} fail.", metadata.getAddress(), metadata.getPort());
                channelFuture.cause().printStackTrace();
                eventLoopGroup.shutdownGracefully();
            }
        });
        //写入数据
        channelFuture.channel().writeAndFlush(protocol);
        return metadata.getTimeout() != null ? future.get(metadata.getTimeout(), TimeUnit.MILLISECONDS) : future.get();
    }
}