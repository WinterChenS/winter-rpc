package com.winterchen.providerapi.api;

import com.winterchen.providerapi.request.UserRequest;
import com.winterchen.providerapi.response.UserResponse;

import java.util.List;

/**
 * @author CENTURY
 * @version 1.0
 * @date 2021/11/17 13:52
 **/
public interface UserApi {


    UserResponse getById(String id);

    UserResponse addUser(UserRequest userRequest);

    void update(UserRequest userRequest);

    void delete(String id);

    List<UserResponse> list();

}