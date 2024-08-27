package com.example.restapi.mapper;

import com.example.restapi.entity.User;
import com.example.restapi.entity.UserDto;
import com.example.restapi.entity.UserInputDto;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class UserMapper {
    public static User mapInputDtoToEntity(UserInputDto userInputDto) {
        User user = new User();
        user.setUsername(userInputDto.getUsername());
        user.setPassword(new String(Base64.getEncoder().encode(
                userInputDto.getPassword().getBytes(StandardCharsets.UTF_8)
        )));
        user.setName(userInputDto.getName());
        user.setSurname(userInputDto.getSurname());
        return user;
    }

    public static UserDto mapEntityToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setId(user.getId());
        userDto.setFullName(user.getName() + " " + user.getSurname());
        return userDto;
    }
}
