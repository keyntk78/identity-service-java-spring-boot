package com.ntk.identity_service.controller;

import com.ntk.identity_service.dto.request.UserCreationRequest;
import com.ntk.identity_service.dto.request.UserUpdateRequest;
import com.ntk.identity_service.dto.response.ApiResponse;
import com.ntk.identity_service.entity.User;
import com.ntk.identity_service.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService _userService;

    public UserController(UserService userService) {
        _userService = userService;
    }

    @PostMapping
    ApiResponse<User> createUser(@RequestBody @Valid UserCreationRequest request){
        ApiResponse<User> apiResponse = new ApiResponse<>();
        apiResponse.setResult(_userService.createUser(request));

        return apiResponse;
    }


    @GetMapping
    ApiResponse<List<User>> getUsers(){
        ApiResponse<List<User>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(_userService.getUsers());

        return apiResponse;
    }

    @GetMapping("/{userId}")
    ApiResponse<User> getUser(@PathVariable("userId") String userId){
        ApiResponse<User> apiResponse = new ApiResponse<>();
        apiResponse.setResult( _userService.getUser(userId));

        return apiResponse;
    }

    @PutMapping("/{userId}")
    ApiResponse<User> updateUser(@PathVariable String userId,@Valid @RequestBody UserUpdateRequest request){
        ApiResponse<User> apiResponse = new ApiResponse<>();
        apiResponse.setResult(_userService.updateUser(userId, request));

        return apiResponse;
    }


    @DeleteMapping("/{userId}")
    ApiResponse<String> deleteUser(@PathVariable String userId){
        _userService.deleteUser(userId);

        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setResult("User has been deleted");

        return apiResponse;
    }
}
