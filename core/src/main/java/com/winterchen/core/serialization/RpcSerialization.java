package com.winterchen.core.serialization;

import java.io.IOException;

/**
 * @author CENTURY
 * @version 1.0
 * @date 2021/11/16 15:00
 **/
public interface RpcSerialization {

    <T> byte[] serialize(T obj) throws IOException;

    <T> T deserialize(byte[] data, Class<T> clz) throws IOException;

}