package com.binaracademy.binarfud.service;

import com.binaracademy.binarfud.dto.request.CreateUserRequest;
import com.binaracademy.binarfud.dto.request.UpdateUserRequest;
import com.binaracademy.binarfud.dto.response.UserResponse;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    UserResponse addNewUser(CreateUserRequest userRequest);
    void updateUser(String username, UpdateUserRequest updateUserRequest);
    void deleteUser(String username);
}
