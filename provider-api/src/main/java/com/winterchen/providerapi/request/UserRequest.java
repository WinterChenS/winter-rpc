package com.winterchen.providerapi.request;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author CENTURY
 * @version 1.0
 * @date 2021/11/17 13:54
 **/
@Data
@Builder
public class UserRequest implements Serializable {


    private static final long serialVersionUID = 7883547796325624200L;

    private String id;

    private Integer age;

    private String name;



}