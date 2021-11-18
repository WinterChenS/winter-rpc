package com.winterchen.core.serialization;

/**
 * @author CENTURY
 * @version 1.0
 * @date 2021/11/16 14:59
 * @description 序列化工厂
 **/
public class SerializationFactory {

    public static RpcSerialization getRpcSerialization(SerializationTypeEnum typeEnum) {
        switch (typeEnum) {
            case HESSIAN:
                return new HessianSerialization();
            case JSON:
                return new JsonSerialization();
            default:
                throw new IllegalArgumentException("serialization type is illegal");
        }
    }

}