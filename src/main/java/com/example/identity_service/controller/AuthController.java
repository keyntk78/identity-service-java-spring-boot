package com.example.identity_service.controller;


import com.example.identity_service.dto.request.LoginRequest;
import com.example.identity_service.dto.response.LoginRespone;
import com.example.identity_service.exception.ApiResponse;
import com.example.identity_service.service.AuthService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {
    AuthService authService;
    @PostMapping("/login")
    ApiResponse<LoginRespone> authenticate(@RequestBody LoginRequest request){
        boolean result = authService.authenticate(request);
        return ApiResponse.<LoginRespone>builder()
                .result(LoginRespone.builder()
                        .authenticated(result)
                        .build())
                .build();
    }
}
