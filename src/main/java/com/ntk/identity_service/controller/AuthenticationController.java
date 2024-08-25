package com.ntk.identity_service.controller;

import com.nimbusds.jose.JOSEException;
import com.ntk.identity_service.dto.request.AuthenticationRequest;
import com.ntk.identity_service.dto.request.IntrospectRequest;
import com.ntk.identity_service.dto.response.ApiResponse;
import com.ntk.identity_service.dto.response.AuthenticationResponse;
import com.ntk.identity_service.dto.response.IntrospectResponse;
import com.ntk.identity_service.service.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService _authenticationService;

    @PostMapping("/login")
    ApiResponse<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse result =  _authenticationService.authenticate(request);

        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }

    //verify token
    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {
        var result = _authenticationService.introspect(request);
        return ApiResponse.<IntrospectResponse>builder()
                .result(result)
                .build();
    }
}
