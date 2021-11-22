package com.winterchen.core.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author winterchen
 * @version 1.0
 * @date 2021/11/16 11:01
 * @description
 **/
@Getter
@AllArgsConstructor
public enum ResultCode {

    OK(200, "success"),
    NOT_FOUND(404, "resource not found"),
    ERROR(500, "server error"),
    ;

    private final Integer code;

    private final String desc;


}