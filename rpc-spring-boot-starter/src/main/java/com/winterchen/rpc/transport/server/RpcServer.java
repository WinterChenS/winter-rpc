package com.winterchen.rpc.transport.server;

/**
 * @author winterchen
 * @version 1.0
 * @date 2021/11/17 10:23
 **/
public interface RpcServer {

    /**
     * 开启服务
     * @param port
     */
    void start(int port);

}