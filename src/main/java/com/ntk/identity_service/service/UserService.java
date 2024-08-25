package com.ntk.identity_service.service;

import com.ntk.identity_service.dto.request.UserCreationRequest;
import com.ntk.identity_service.dto.request.UserUpdateRequest;
import com.ntk.identity_service.dto.response.UserResponse;
import com.ntk.identity_service.entity.User;
import com.ntk.identity_service.enums.Role;
import com.ntk.identity_service.exception.AppException;
import com.ntk.identity_service.exception.ErrorCode;
import com.ntk.identity_service.mapper.UserMapper;
import com.ntk.identity_service.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    UserRepository _userRepository;
   UserMapper _userMapper;
   PasswordEncoder _passwordEncoder;


    public UserResponse createUser(UserCreationRequest request){
        if (_userRepository.existsByUsername(request.getUsername()))
            throw new AppException(ErrorCode.USER_EXISTED);
        User user  = _userMapper.toUser(request);
        user.setPassword(_passwordEncoder.encode(request.getPassword()));
        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());
        user.setRoles(roles);

        return  _userMapper.toUserResponse(_userRepository.save(user));
    }


    public UserResponse updateUser(String userId, UserUpdateRequest request) {
        User user = _userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        _userMapper.updateUser(user, request);

        return _userMapper.toUserResponse(_userRepository.save(user));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    public List<UserResponse> getUsers(){
        return _userRepository.findAll().stream()
                .map(_userMapper::toUserResponse).toList();
    }

    public void deleteUser(String userId){
        _userRepository.deleteById(userId);
    }

    public UserResponse getUser(String id){
        return _userMapper.toUserResponse(_userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found")));
    }

    public UserResponse getMyInfo(){
        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();

        User user = _userRepository.findByUsername(username).orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));

        return _userMapper.toUserResponse(user);
    }
}
