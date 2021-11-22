package com.winterchen.core.exception;

/**
 * @author winterchen
 * @version 1.0
 * @date 2021/11/17 9:25
 **/
public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 3365624081242234230L;

    public ResourceNotFoundException() {
        super();
    }

    public ResourceNotFoundException(String msg) {
        super(msg);
    }

    public ResourceNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public ResourceNotFoundException(Throwable cause) {
        super(cause);
    }
}
