package obss.project.basket.service;

import obss.project.basket.dto.ProductDto;
import obss.project.basket.dto.UserDto;
import obss.project.basket.dto.UserInputDto;
import obss.project.basket.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    UserInputDto updateUser(UserInputDto userInputDto, Long id);

    void removeSellerFromBlacklist(Long userId, Long sellerId);

    List<UserDto> findAllUsers();
    User findByEmail(String email);
    boolean deleteUser(Long id);
    UserDto findById(Long id);
    void addSellerToBlacklist(Long userId, Long sellerId);
}

