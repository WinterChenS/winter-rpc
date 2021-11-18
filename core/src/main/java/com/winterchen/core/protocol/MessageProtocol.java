package com.winterchen.core.protocol;

import lombok.Data;

import java.io.Serializable;

/**
 * @author CENTURY
 * @version 1.0
 * @date 2021/11/17 9:27
 * @description TODO
 **/
@Data
public class MessageProtocol<T> implements Serializable {

    private static final long serialVersionUID = -2240569669802542242L;

    /**
     *  消息头
     */
    private MessageHeader header;

    /**
     *  消息体
     */
    private T body;

}