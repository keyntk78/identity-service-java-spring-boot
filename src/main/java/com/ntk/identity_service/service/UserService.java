package com.ntk.identity_service.service;

import com.ntk.identity_service.dto.request.UserCreationRequest;
import com.ntk.identity_service.dto.request.UserUpdateRequest;
import com.ntk.identity_service.entity.User;
import com.ntk.identity_service.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private UserRepository _userRepository;

    public UserService(UserRepository userRepository) {
        _userRepository = userRepository;
    }

    public User createUser(UserCreationRequest request){
        User user = new User();

        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setDob(request.getDob());

        return _userRepository.save(user);
    }


    public User updateUser(String userId, UserUpdateRequest request) {
        User user = getUser(userId);

        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setDob(request.getDob());

        return _userRepository.save(user);
    }

    public List<User> getUsers(){
        return _userRepository.findAll();
    }

    public void deleteUser(String userId){
        _userRepository.deleteById(userId);
    }

    public User getUser(String id){
        return _userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
