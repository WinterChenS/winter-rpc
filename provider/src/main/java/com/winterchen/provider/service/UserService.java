package com.winterchen.provider.service;

import com.winterchen.provider.entity.User;
import com.winterchen.provider.store.UserInfoCache;
import com.winterchen.providerapi.api.UserApi;
import com.winterchen.providerapi.request.UserRequest;
import com.winterchen.providerapi.response.UserResponse;
import com.winterchen.rpc.annotation.RpcService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author CENTURY
 * @version 1.0
 * @date 2021/11/17 14:10
 **/
@RpcService(interfaceType = UserApi.class, version = "1.0")
public class UserService implements UserApi {


    @Autowired
    private UserInfoCache userInfoCache;


    @Override
    public UserResponse getById(String id) {
        return toConvertToResponse(userInfoCache.findById(id));
    }

    @Override
    public UserResponse addUser(UserRequest userRequest) {
        User user = toConvertToUser(userRequest);
        userInfoCache.add(user);
        return toConvertToResponse(user);
    }

    @Override
    public void update(UserRequest userRequest) {
        User user = toConvertToUser(userRequest);
        userInfoCache.update(user);
    }

    @Override
    public void delete(String id) {
        userInfoCache.delete(id);
    }

    @Override
    public List<UserResponse> list() {
        return toConvertToResponses(userInfoCache.list());
    }

    private List<UserResponse> toConvertToResponses(List<User> users) {
        if (CollectionUtils.isEmpty(users)) {
            return Collections.emptyList();
        }
        List<UserResponse> result = new ArrayList<>(users.size());
        for (User user : users) {
            result.add(toConvertToResponse(user));
        }
        return result;
    }

    private UserResponse toConvertToResponse(User user) {
        if (user == null) {
            return null;
        }
        UserResponse response = new UserResponse();
        BeanUtils.copyProperties(user, response);
        return response;
    }

    private User toConvertToUser(UserRequest userRequest) {
        if (userRequest == null) {
            return null;
        }
        User user = new User();
        BeanUtils.copyProperties(userRequest, user);
        return user;
    }


}