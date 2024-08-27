package obss.project.basket.service;

import obss.project.basket.dto.LoginDto;
import obss.project.basket.dto.RegisterDto;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    String login(LoginDto loginDto);

    String register(RegisterDto registerDto, String role);
}