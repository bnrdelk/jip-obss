package com.example.restapi.service;

import com.example.restapi.entity.UserDto;
import com.example.restapi.entity.UserInputDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    UserDto saveUser(UserInputDto userInputDto);
    List<UserDto> findAllUsers();
    UserDto findUserByUsername(String name);
    UserDto updateUser(UserInputDto userInputDto, String username);
    boolean deleteUser(String name);
}
