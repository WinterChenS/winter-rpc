package com.winterchen.core.serialization;

/**
 * @author CENTURY
 * @version 1.0
 * @date 2021/11/16 15:08
 * @description 序列化异常
 **/
public class SerializationException extends RuntimeException {

    private static final long serialVersionUID = 3365624081242234230L;

    public SerializationException() {
        super();
    }

    public SerializationException(String msg) {
        super(msg);
    }

    public SerializationException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public SerializationException(Throwable cause) {
        super(cause);
    }
}
