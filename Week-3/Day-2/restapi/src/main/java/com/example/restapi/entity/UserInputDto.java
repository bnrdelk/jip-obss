package com.example.restapi.entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserInputDto implements Serializable {
    @NotNull
    @Size(min = 2, max = 10, message = "Username must be longer than 2, smaller than 10 characters")
    private String username;

    @NotNull
    @Size(min = 4, max = 12)
    private String password;

    @Size(min = 0, max = 24)
    private String name;

    @Size(min = 0, max = 24)
    private String surname;
}
