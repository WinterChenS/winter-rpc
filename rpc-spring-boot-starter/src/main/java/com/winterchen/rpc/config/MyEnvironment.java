package com.winterchen.rpc.config;

import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @author CENTURY
 * @version 1.0
 * @date 2021/11/17 18:04
 * @description TODO
 **/
@Configuration
public class MyEnvironment implements EnvironmentAware {
    @Override
    public void setEnvironment(Environment environment) {
        System.out.println(environment);
    }
}