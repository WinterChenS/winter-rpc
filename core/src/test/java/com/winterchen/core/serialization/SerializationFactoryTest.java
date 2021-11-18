package com.winterchen.core.serialization;

import com.winterchen.core.common.RpcResponse;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class SerializationFactoryTest {


    @SneakyThrows
    @Test
    public void testSerialze() {

        RpcSerialization rpcSerialization = SerializationFactory.getRpcSerialization(SerializationTypeEnum.JSON);
        RpcResponse<String> ok = RpcResponse.ok("this is a string");
        byte[] bytes = rpcSerialization.serialize(ok);
        RpcResponse deserialize = rpcSerialization.deserialize(bytes, RpcResponse.class);
        assertEquals(ok, deserialize);

    }

}