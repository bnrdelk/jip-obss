package obss.project.basket.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import obss.project.basket.constant.ApiResponseCode;
import obss.project.basket.dto.*;
import obss.project.basket.entity.User;
import obss.project.basket.repository.UserRepository;
import obss.project.basket.service.AuthService;
import obss.project.basket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1/auth")
@Tag(name = "Auth Management", description = "Endpoints for managing logins and registrations.")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    public AuthController(AuthService authService, UserService userService, UserRepository userRepository) {
        this.authService = authService;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    @Operation(summary = "Login", description = "Login into the system with mail and password.")
    public ApiResponse<?> login(@RequestBody LoginDto loginDto) {

        String token = authService.login(loginDto);

        AuthResponseDto authResponseDto = new AuthResponseDto();
        authResponseDto.setAccessToken(token);

        Optional<User> user = userRepository.findByEmail(loginDto.getEmail());

        authResponseDto.setUserId(user.get().getId());
        authResponseDto.setEmail(loginDto.getEmail());

        authResponseDto.setRoles(user.get().getRoles());
        return ApiResponse.success(authResponseDto, "Login is successful.", ApiResponseCode.OK);
    }

    @PostMapping("/register/{role}")
    @Operation(summary = "Register the user", description = "Register into the system with mail, password, name, surname.")
    public ApiResponse<?> register(@Valid @RequestBody RegisterDto registerDto, @PathVariable String role) {
        try {
            String message = authService.register(registerDto, role);
            return ApiResponse.success(ApiResponseCode.CREATED, message);
        } catch (RuntimeException e) {
            return ApiResponse.error(e.getMessage(), ApiResponseCode.BAD_REQUEST);
        }
    }
}
