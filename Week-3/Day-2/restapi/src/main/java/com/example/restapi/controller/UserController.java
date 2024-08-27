package com.example.restapi.controller;

import com.example.restapi.constant.ApiResponseCode;
import com.example.restapi.dto.ApiResponse;
import com.example.restapi.entity.UserDto;
import com.example.restapi.entity.UserInputDto;
import com.example.restapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@Tag(name = "User Management", description = "Endpoints for managing users")
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @Operation(summary = "Create a new user", description = "Add a new user to the system.")
    public ApiResponse<UserDto> saveUser(@RequestBody UserInputDto userInputDto) {
        return ApiResponse.of(userService.saveUser(userInputDto));
    }

    @PutMapping("/{username}")
    @Operation(summary = "Update given user", description = "Update the user in the system.")
    public ApiResponse<?> updateUser(@RequestBody UserInputDto userInputDto, @PathVariable String username) {
        UserDto updatedUser = userService.updateUser(userInputDto, username);
        if (updatedUser != null) {
            return ApiResponse.of(updatedUser);
        } else {
            return ApiResponse.of("User not found!");
        }
    }

    @DeleteMapping("/{username}")
    @Operation(summary = "Delete given user", description = "Delete the user in the system.")
    public ApiResponse<?> deleteUser(@PathVariable String username) {
        boolean isDeleted = userService.deleteUser(username);
        if (isDeleted) {
            return ApiResponse.ofCode(ApiResponseCode.OK);
        } else {
            return ApiResponse.of("User not found!");
        }
    }

    @GetMapping
    @Operation(summary = "Get all users", description = "Get all users in the system.")
    public ApiResponse<Collection<UserDto>> getAllUsers() {
        return ApiResponse.of(userService.findAllUsers());
    }

    @GetMapping("/{username}")
    @Operation(summary = "Get given user", description = "Get the user in the system by given name.")
    public ApiResponse<?> getUserByUsername(@PathVariable String username) {
        UserDto userDto = userService.findUserByUsername(username);
        if (userDto != null) {
            return ApiResponse.of(userDto);
        } else {
            return ApiResponse.of("User not found!");
        }
    }
}
