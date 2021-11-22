package com.winterchen.core.codec;

import com.winterchen.core.common.RpcRequest;
import com.winterchen.core.common.RpcResponse;
import com.winterchen.core.protocol.MessageHeader;
import com.winterchen.core.protocol.MessageProtocol;
import com.winterchen.core.protocol.MsgType;
import com.winterchen.core.protocol.ProtocolConstants;
import com.winterchen.core.serialization.RpcSerialization;
import com.winterchen.core.serialization.SerializationFactory;
import com.winterchen.core.serialization.SerializationTypeEnum;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author winterchen
 * @version 1.0
 * @date 2021/11/16 14:37
 * @description TODO
 **/
@Slf4j
public class RpcDecoder extends ByteToMessageDecoder {

    /**
     *
     *  +---------------------------------------------------------------+
     *  | 魔数 2byte | 协议版本号 1byte | 序列化算法 1byte | 报文类型 1byte|
     *  +---------------------------------------------------------------+
     *  | 状态 1byte |        消息 ID 8byte     |      数据长度 4byte     |
     *  +---------------------------------------------------------------+
     *  |                   数据内容 （长度不定）                         |
     *  +---------------------------------------------------------------+
     *
     *  decode 这个方法会被循环调用
     * @param ctx
     * @param in
     * @param out
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < ProtocolConstants.HEADER_TOTAL_LEN) {
            log.error("可读数据小于请求头总的大小，直接跳过");
            return;
        }
        // 标记 Bytebuf 读指针位置
        in.markReaderIndex();

        // 魔数
        short magic = in.readShort();
        if (magic != ProtocolConstants.MAGIC) {
            throw new IllegalArgumentException("magic number is illegal, " + magic);
        }
        //协议版本号
        byte version = in.readByte();
        //序列化算法
        byte serializeType = in.readByte();
        //报文类型
        byte msgType = in.readByte();
        //状态
        byte status = in.readByte();
        //消息ID
        CharSequence requestId = in.readCharSequence(ProtocolConstants.REQ_LEN, StandardCharsets.UTF_8);
        //数据长度
        int dataLength = in.readInt();
        //数据
        byte[] data = new byte[dataLength];
        in.readBytes(data);
        MsgType msgTypeEnum = MsgType.findByType(msgType);

        if (msgTypeEnum == null) {
            log.error("error msg type");
            return;
        }

        MessageHeader header = new MessageHeader();
        header.setMagic(magic);
        header.setVersion(version);
        header.setSerialization(serializeType);
        header.setStatus(status);
        header.setRequestId(String.valueOf(requestId));
        header.setMsgType(msgType);
        header.setMsgLen(dataLength);

        RpcSerialization rpcSerialization = SerializationFactory.getRpcSerialization(SerializationTypeEnum.parseByType(serializeType));
        switch (msgTypeEnum) {
            case REQUEST:
                RpcRequest request = rpcSerialization.deserialize(data, RpcRequest.class);
                if (request != null) {
                    MessageProtocol<RpcRequest> protocol = new MessageProtocol<>();
                    protocol.setHeader(header);
                    protocol.setBody(request);
                    out.add(protocol);
                }
                break;
            case RESPONSE:
                RpcResponse response = rpcSerialization.deserialize(data, RpcResponse.class);
                if (response != null) {
                    MessageProtocol<RpcResponse> protocol = new MessageProtocol<>();
                    protocol.setHeader(header);
                    protocol.setBody(response);
                    out.add(protocol);
                }
                break;
        }
    }

}