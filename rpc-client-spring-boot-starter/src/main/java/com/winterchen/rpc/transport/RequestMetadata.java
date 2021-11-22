package com.winterchen.rpc.transport;

import com.winterchen.core.common.RpcRequest;
import com.winterchen.core.protocol.MessageProtocol;
import lombok.Builder;
import lombok.Data;

/**
 * @author CENTURY
 * @version 1.0
 * @date 2021/11/17 13:11
 * @description 请求元数据
 **/
@Data
@Builder
public class RequestMetadata {

    /**
     *  协议
     */
    private MessageProtocol<RpcRequest> protocol;

    /**
     *  地址
     */
    private String address;

    /**
     *  端口
     */
    private Integer port;

    /**
     *  服务调用超时
     */
    private Integer timeout;

}