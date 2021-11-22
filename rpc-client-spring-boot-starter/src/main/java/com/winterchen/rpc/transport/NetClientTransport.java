package com.winterchen.rpc.transport;

import com.winterchen.core.common.RpcResponse;
import com.winterchen.core.protocol.MessageProtocol;

/**
 * @author CENTURY
 * @version 1.0
 * @date 2021/11/17 13:10
 * @description TODO
 **/
public interface NetClientTransport {

    /**
     *  发送数据
     * @param metadata
     * @return
     * @throws Exception
     */
    MessageProtocol<RpcResponse> sendRequest(RequestMetadata metadata) throws Exception;

}
