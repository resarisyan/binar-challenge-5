package com.binaracademy.binarfud.service;

import com.binaracademy.binarfud.dto.request.CreateUserRequest;
import com.binaracademy.binarfud.dto.request.UpdateUserRequest;
import com.binaracademy.binarfud.dto.response.UserResponse;
import com.binaracademy.binarfud.entity.User;
import com.binaracademy.binarfud.exception.DataConflictException;
import com.binaracademy.binarfud.exception.DataNotFoundException;
import com.binaracademy.binarfud.exception.ServiceBusinessException;
import com.binaracademy.binarfud.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    public UserResponse addNewUser(CreateUserRequest userRequest) throws ServiceBusinessException {
        UserResponse userResponse;
        try{
            log.info("Adding new user");
            User user = User.builder()
                    .username(userRequest.getUsername())
                    .email(userRequest.getEmail())
                    .password(userRequest.getPassword())
                    .build();
            userRepository.save(user);
            userResponse = UserResponse.builder()
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .build();
        }
        catch (DataConflictException e) {
            throw e;
        } catch (Exception e) {
            log.error("Failed to add new user");
            throw new ServiceBusinessException("Failed to add new user");
        }

        log.info("User {} successfully added", userResponse.getUsername());
        return userResponse;
    }

    public void updateUser(String username, UpdateUserRequest updateUserRequest) {
        try {
            log.info("Updating user");
            User user = userRepository.findFirstByUsername(username)
                    .orElseThrow(() -> new DataNotFoundException("User not found"));
            user.setUsername(updateUserRequest.getUsername());
            user.setEmail(updateUserRequest.getEmail());
            user.setPassword(updateUserRequest.getPassword());
            userRepository.save(user);
            log.info("User {} successfully updated", user.getUsername());
        } catch (DataNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Failed to update user");
            throw new ServiceBusinessException("Failed to update user");
        }
    }

    public void deleteUser(String username) {
        log.info("Deleting user");
        try {
            User user = userRepository.findFirstByUsername(username)
                    .orElseThrow(() -> new DataNotFoundException("User not found"));
            userRepository.delete(user);
            log.info("User {} successfully deleted", user.getUsername());
        } catch (DataNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Failed to delete user");
            throw new ServiceBusinessException("Failed to delete user");
        }
    }
}
