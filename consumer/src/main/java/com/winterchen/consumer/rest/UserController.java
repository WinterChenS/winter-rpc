package com.winterchen.consumer.rest;

import com.winterchen.providerapi.api.UserApi;
import com.winterchen.providerapi.request.UserRequest;
import com.winterchen.providerapi.response.UserResponse;
import com.winterchen.rpc.annotation.RpcAutowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author winterchen
 * @version 1.0
 * @date 2021/11/17 14:22
 **/
@RestController
@RequestMapping("/user")
public class UserController {

    @RpcAutowired(version = "1.0")
    private UserApi userApi;


    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(
            @PathVariable("id")
            String id
    ) {
        return ResponseEntity.ok(userApi.getById(id));
    }

    @PostMapping("")
    public ResponseEntity<UserResponse> addUser(
            @RequestBody
            UserRequest userRequest
    ) {
        return ResponseEntity.ok(userApi.addUser(userRequest));
    }

    @PutMapping("")
    public ResponseEntity<String> update(
            @RequestBody
            UserRequest userRequest
    ) {
        userApi.update(userRequest);
        return ResponseEntity.ok("success");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(String id) {
        userApi.delete(id);
        return ResponseEntity.ok("success");
    }

    @GetMapping("/list")
    public ResponseEntity<List<UserResponse>> list() {
        return ResponseEntity.ok(userApi.list());
    }

}