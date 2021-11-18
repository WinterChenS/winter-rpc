package com.winterchen.core.protocol;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author CENTURY
 * @version 1.0
 * @date 2021/11/16 14:54
 * @description 消息类型
 **/
@Getter
@AllArgsConstructor
public enum MsgType {

    REQUEST((byte) 1),
    RESPONSE((byte) 2);

    private byte type;


    public static MsgType findByType(byte type) {
        for (MsgType msgType : MsgType.values()) {
            if (msgType.getType() == type) {
                return msgType;
            }
        }
        return null;
    }

}