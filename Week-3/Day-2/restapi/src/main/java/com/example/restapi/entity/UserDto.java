package com.example.restapi.entity;

import lombok.Data;

@Data
public class UserDto {
    Long id;
    String username;
    String fullName;
}
