package com.winterchen.consumer.rest;

import com.winterchen.consumer.service.UserService;
import com.winterchen.providerapi.request.UserRequest;
import com.winterchen.providerapi.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author CENTURY
 * @version 1.0
 * @date 2021/11/17 14:22
 **/
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/{id}")
    public UserResponse getById(
            @PathVariable("id")
            String id
    ) {
        return userService.getById(id);
    }

    @PostMapping("")
    public UserResponse addUser(
            @RequestBody
            UserRequest userRequest
    ) {
        return userService.addUser(userRequest);
    }

    @PutMapping("")
    public String update(
            @RequestBody
            UserRequest userRequest
    ) {
        userService.update(userRequest);
        return "success";
    }

    @DeleteMapping("/{id}")
    public String delete(String id) {
        userService.delete(id);
        return "success";
    }

    @GetMapping("/list")
    public List<UserResponse> list() {
        return userService.list();
    }

}