package com.winterchen.core.codec;

import com.winterchen.core.protocol.MessageHeader;
import com.winterchen.core.protocol.MessageProtocol;
import com.winterchen.core.serialization.RpcSerialization;
import com.winterchen.core.serialization.SerializationFactory;
import com.winterchen.core.serialization.SerializationTypeEnum;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.StandardCharsets;

/**
 * @author winterchen
 * @version 1.0
 * @date 2021/11/17 10:32
 **/
public class RpcEncoder<T> extends MessageToByteEncoder<MessageProtocol<T>> {


    /**
     *
     *  +---------------------------------------------------------------+
     *  | 魔数 2byte | 协议版本号 1byte | 序列化算法 1byte | 报文类型 1byte|
     *  +---------------------------------------------------------------+
     *  | 状态 1byte |        消息 ID 32byte     |      数据长度 4byte    |
     *  +---------------------------------------------------------------+
     *  |                   数据内容 （长度不定）                         |
     *  +---------------------------------------------------------------+
     *
     *
     * @param channelHandlerContext
     * @param messageProtocol
     * @param byteBuf
     * @throws Exception
     */
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, MessageProtocol<T> messageProtocol, ByteBuf byteBuf) throws Exception {
        MessageHeader header = messageProtocol.getHeader();
        // 魔数
        byteBuf.writeShort(header.getMagic());

        // 协议版本号
        byteBuf.writeByte(header.getVersion());

        // 序列化算法
        byteBuf.writeByte(header.getSerialization());

        // 报文类型
        byteBuf.writeByte(header.getMsgType());

        // 状态
        byteBuf.writeByte(header.getStatus());

        // 消息 ID
        byteBuf.writeCharSequence(header.getRequestId(), StandardCharsets.UTF_8);

        RpcSerialization serialization = SerializationFactory.getRpcSerialization(SerializationTypeEnum.parseByType(header.getSerialization()));
        byte[] data = serialization.serialize(messageProtocol.getBody());
        byteBuf.writeInt(data.length);
        byteBuf.writeBytes(data);

    }
}