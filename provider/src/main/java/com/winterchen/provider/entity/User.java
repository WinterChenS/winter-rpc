package com.winterchen.provider.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author winterchen
 * @version 1.0
 * @date 2021/11/17 14:00
 * @description TODO
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {


    private static final long serialVersionUID = -362216111452121920L;

    private String id;

    private Integer age;

    private String name;

}