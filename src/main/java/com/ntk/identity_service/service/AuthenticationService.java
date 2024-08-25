package com.ntk.identity_service.service;

import com.ntk.identity_service.dto.request.AuthenticationRequest;
import com.ntk.identity_service.exception.AppException;
import com.ntk.identity_service.exception.ErrorCode;
import com.ntk.identity_service.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    UserRepository _userRepository;

    public boolean authenticate(AuthenticationRequest request) {
        var user = _userRepository.findByUsername(request.getUsername())
                            .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        return passwordEncoder.matches(request.getPassword(), user.getPassword());
    }
}
