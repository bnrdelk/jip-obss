package obss.project.basket.service.Impl;

import obss.project.basket.dto.*;
import obss.project.basket.entity.Blacklist;
import obss.project.basket.entity.Seller;
import obss.project.basket.entity.User;
import obss.project.basket.mapper.UserMapper;
import obss.project.basket.repository.BlackListedRepository;
import obss.project.basket.repository.ProductRepository;
import obss.project.basket.repository.SellerRepository;
import obss.project.basket.repository.UserRepository;
import obss.project.basket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final BlackListedRepository blackListedRepository;
    private final PasswordEncoder passwordEncoder;
    private final SellerRepository sellerRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ProductRepository productRepository, BlackListedRepository blackListedRepository, PasswordEncoder passwordEncoder, SellerRepository sellerRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.blackListedRepository = blackListedRepository;
        this.passwordEncoder = passwordEncoder;
        this.sellerRepository = sellerRepository;
    }

    @Override
    public UserInputDto updateUser(UserInputDto userInputDto, Long id) {
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            return null;
        }

        user.setEmail(userInputDto.getEmail());
        user.setName(userInputDto.getName());
        user.setSurname(userInputDto.getSurname());

        if (userInputDto.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(userInputDto.getPassword()));
        }

        User savedUser = userRepository.save(user);

        return UserMapper.mapEntityToInputDto(savedUser);
    }

    @Override
    public void removeSellerFromBlacklist(Long userId, Long sellerId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found for id: " + userId));
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new RuntimeException("Seller not found for id: " + sellerId));

        Blacklist blacklistEntry = blackListedRepository.findByUserAndSeller(user, seller)
                .orElseThrow(() -> new RuntimeException("Blacklist entry not found for seller and user"));

        blackListedRepository.delete(blacklistEntry);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(UserMapper::mapEntityToDto).collect(Collectors.toList());
    }

    @Override
    public UserDto findById(Long id) {
        return userRepository.findById(id)
                .map(UserMapper::mapEntityToDto)
                .orElse(null);
    }

/*
    @Override
    public UserDto updateUser(RegisterDto userInputDto, Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setPassword(new String(Base64.getEncoder().encode(
                    userInputDto.getPassword().getBytes(StandardCharsets.UTF_8)
            )));
            user.setName(userInputDto.getName());
            user.setSurname(userInputDto.getSurname());
            userRepository.save(user);
            return UserMapper.mapEntityToDto(user);
        }
        return null;
    }*/

    @Override
    public boolean deleteUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            userRepository.delete(user);
            return true;
        }
        return false;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found for email: " + email));
    }

    @Override
    public void addSellerToBlacklist(Long userId, Long sellerId) {
        if (userId.equals(sellerId)) {
            throw new RuntimeException("You cannot blacklist yourself");
        }

        User user = userRepository.findById(userId)
                .orElseThrow();
        User seller = userRepository.findById(sellerId)
                .orElseThrow();

        user.getBlackListedSellers().add((Seller) seller);
        userRepository.save(user);
    }
}
