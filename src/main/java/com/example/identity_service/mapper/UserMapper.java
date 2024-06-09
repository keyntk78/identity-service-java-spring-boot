package com.example.identity_service.mapper;

import com.example.identity_service.dto.UserCreationRequest;
import com.example.identity_service.dto.UserResponse;
import com.example.identity_service.dto.UserUpdateRequest;
import com.example.identity_service.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);
    UserResponse toUserResponse(User user);
    void updateUser(@MappingTarget User user, UserUpdateRequest request);

}
