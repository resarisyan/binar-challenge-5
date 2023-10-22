package com.binaracademy.binarfud.controller;

import com.binaracademy.binarfud.dto.request.CreateUserRequest;
import com.binaracademy.binarfud.dto.request.UpdateUserRequest;
import com.binaracademy.binarfud.dto.response.base.APIResponse;
import com.binaracademy.binarfud.dto.response.base.APIResultResponse;
import com.binaracademy.binarfud.dto.response.UserResponse;
import com.binaracademy.binarfud.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private UserService userService;

    @PostMapping
    @Schema(name = "CreateUserRequest", description = "Create user request body")
    @Operation(summary = "Endpoint to handle create new user")
    public ResponseEntity<APIResultResponse<UserResponse>> createNewUser(@RequestBody @Valid CreateUserRequest createUserRequest) {
        UserResponse userResponse = userService.addNewUser(createUserRequest);
        APIResultResponse<UserResponse> responseDTO = new APIResultResponse<>(
                HttpStatus.CREATED,
                "User successfully created",
                userResponse
        );
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{username}")
    @Schema(name = "UpdateUserRequest", description = "Update user request body")
    @Operation(summary = "Endpoint to handle update user")
    public ResponseEntity<APIResultResponse<UserResponse>> updateUser(@RequestBody @Valid UpdateUserRequest updateUserRequest, @PathVariable("username") String username){
        userService.updateUser(username, updateUserRequest);
        APIResultResponse<UserResponse> responseDTO = new APIResultResponse<>(
                HttpStatus.OK,
                "User successfully updated"
        );
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{username}")
    @Schema(name = "DeleteUserRequest", description = "Delete user request body")
    @Operation(summary = "Endpoint to handle delete user")
    public ResponseEntity<APIResponse> deleteUser(@PathVariable("username") String username){
        userService.deleteUser(username);
        APIResponse responseDTO =  new APIResponse(
                HttpStatus.OK,
                "User successfully deleted"
        );
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
