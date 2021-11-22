package com.winterchen.providerapi.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author winterchen
 * @version 1.0
 * @date 2021/11/17 13:55
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse implements Serializable {

    private static final long serialVersionUID = -6638007436963188344L;

    private String id;

    private Integer age;

    private String name;
}