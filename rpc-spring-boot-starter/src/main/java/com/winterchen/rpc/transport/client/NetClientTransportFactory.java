package com.winterchen.rpc.transport.client;

import lombok.extern.slf4j.Slf4j;

/**
 * @author winterchen
 * @version 1.0
 * @date 2021/11/17 13:10
 **/
@Slf4j
public class NetClientTransportFactory {

    public static NetClientTransport getNetClientTransport(){
        return new NettyNetClientTransport();
    }


}