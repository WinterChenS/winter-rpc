package com.winterchen.core.protocol;

import lombok.Getter;

/**
 * @author winterchen
 * @version 1.0
 * @date 2021/11/17 9:27
 **/
public enum MsgStatus {
    SUCCESS((byte)0),
    FAIL((byte)1);

    @Getter
    private final byte code;

    MsgStatus(byte code) {
        this.code = code;
    }

    public static boolean isSuccess(byte code){
        return MsgStatus.SUCCESS.code == code;
    }

}