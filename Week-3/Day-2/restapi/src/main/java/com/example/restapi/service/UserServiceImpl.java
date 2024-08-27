package com.example.restapi.service;

import com.example.restapi.entity.User;
import com.example.restapi.entity.UserDto;
import com.example.restapi.entity.UserInputDto;
import com.example.restapi.mapper.UserMapper;
import com.example.restapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto saveUser(UserInputDto userInputDto) {
        User user = UserMapper.mapInputDtoToEntity(userInputDto);
        userRepository.save(user);
        return UserMapper.mapEntityToDto(user);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(UserMapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto findUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return UserMapper.mapEntityToDto(user);
        }
        return null;
    }

    @Override
    public UserDto updateUser(UserInputDto userInputDto, String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            user.setUsername(userInputDto.getUsername());
            user.setPassword(new String(Base64.getEncoder().encode(
                    userInputDto.getPassword().getBytes(StandardCharsets.UTF_8)
            )));
            user.setName(userInputDto.getName());
            user.setSurname(userInputDto.getSurname());
            userRepository.save(user);
            return UserMapper.mapEntityToDto(user);
        }
        return null;
    }

    @Override
    public boolean deleteUser(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            userRepository.delete(user);
            return true;
        }
        return false;
    }
}
