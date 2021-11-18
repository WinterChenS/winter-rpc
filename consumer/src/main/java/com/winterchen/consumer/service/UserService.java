package com.winterchen.consumer.service;

import com.winterchen.providerapi.api.UserApi;
import com.winterchen.providerapi.request.UserRequest;
import com.winterchen.providerapi.response.UserResponse;
import com.winterchen.rpc.annotation.RpcAutowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author CENTURY
 * @version 1.0
 * @date 2021/11/17 14:19
 **/
@Service
public class UserService {

    @RpcAutowired
    private UserApi userApi;

    public UserResponse getById(String id) {
        return userApi.getById(id);
    }

    public UserResponse addUser(UserRequest userRequest) {
        return userApi.addUser(userRequest);
    }

    public void update(UserRequest userRequest) {
        userApi.update(userRequest);
    }

    public void delete(String id) {
        userApi.delete(id);
    }

    public List<UserResponse> list() {
        return userApi.list();
    }
}