package com.winterchen.rpc.transport;

import lombok.extern.slf4j.Slf4j;

/**
 * @author CENTURY
 * @version 1.0
 * @date 2021/11/17 13:10
 **/
@Slf4j
public class NetClientTransportFactory {

    public static NetClientTransport getNetClientTransport(){
        return new NettyNetClientTransport();
    }


}