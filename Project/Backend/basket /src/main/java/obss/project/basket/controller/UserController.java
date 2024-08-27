package obss.project.basket.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import obss.project.basket.constant.ApiResponseCode;
import obss.project.basket.dto.*;
import obss.project.basket.entity.Seller;
import obss.project.basket.entity.User;
import obss.project.basket.mapper.UserMapper;
import obss.project.basket.repository.UserRepository;
import obss.project.basket.service.ProductService;
import obss.project.basket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Validated
@RestController
@Tag(name = "User Management", description = "Endpoints for managing users")
@RequestMapping("/v1/users")
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final UserRepository userRepository;
    private final ProductService productService;
    private final HttpServletResponse httpServletResponse;

    @Autowired
    public UserController(UserService userService, UserRepository userRepository, ProductService productService, HttpServletResponse httpServletResponse, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.productService = productService;
        this.httpServletResponse = httpServletResponse;
        this.passwordEncoder = passwordEncoder;
    }

    /*
    @PostMapping
    @Operation(summary = "Create a new user", description = "Add a new user to the system.")
    public ApiResponse<UserDto> saveUser(@RequestBody UserInputDto userInputDto) {
        return ApiResponse.of(userService.saveUser(userInputDto));
    }*/

    @PutMapping("/{id}")
    @Operation(summary = "Update given user", description = "Update the user in the system.")
    public ApiResponse<?> updateUser(
            @RequestBody @Validated UserInputDto userInputDto,
            @PathVariable Long id) throws Exception {

        UserInputDto updatedUser = userService.updateUser(userInputDto, id);

        if (updatedUser != null) {
            return ApiResponse.success(updatedUser, "User updated successfully.");
        } else {
            return ApiResponse.error("User not found", ApiResponseCode.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete given user", description = "Delete the user in the system.")
    public ApiResponse<?> deleteUser(@PathVariable Long id) {
        boolean isDeleted = userService.deleteUser(id);
        if (isDeleted) {
            return ApiResponse.success("User deleted successfully.");
        } else {
            return ApiResponse.error("User not found", ApiResponseCode.NOT_FOUND);
        }
    }

    @GetMapping
    @Operation(summary = "Get all users", description = "Get all users in the system.")
    public ApiResponse<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.findAllUsers();
        return ApiResponse.success(users, "Users listed successfully.");
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get given user", description = "Get the user in the system by given ID.")
    public ApiResponse<UserDto> getUserById(@PathVariable Long id) {
        UserDto userDto = userService.findById(id);
        if (userDto != null) {
            return ApiResponse.success(userDto, "User listed successfully.");
        } else {
            return ApiResponse.error("User not found!", ApiResponseCode.NOT_FOUND);
        }
    }

    @PostMapping("/{id}/blacklist/{sellerId}")
    @Operation(summary = "Blacklist a seller", description = "Add a seller to the user's blacklist.")
    public ApiResponse<?> blacklistSeller(@PathVariable Long id, @PathVariable Long sellerId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            return ApiResponse.error("User is not authenticated", ApiResponseCode.UNAUTHORIZED);
        }

        String email = authentication.getName();
        User authenticatedUser = userService.findByEmail(email);
        Long authenticatedUserId = authenticatedUser.getId();

        if (!id.equals(authenticatedUserId)) {
            return ApiResponse.error("Unauthorized: Cannot blacklist seller for another user", ApiResponseCode.FORBIDDEN);
        }

        try {
            userService.addSellerToBlacklist(id, sellerId);
            return ApiResponse.success("Seller added to blacklist successfully.");
        } catch (RuntimeException e) {
            return ApiResponse.error(e.getMessage(), ApiResponseCode.NOT_FOUND);
        } catch (Exception e) {
            return ApiResponse.error("An error occurred while blacklisting the seller", ApiResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}/blacklist/{sellerId}")
    @Operation(summary = "Remove a seller from the blacklist", description = "Remove a seller from the user's blacklist.")
    public ApiResponse<?> removeSellerFromBlacklist(@PathVariable Long id, @PathVariable Long sellerId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            return ApiResponse.error("User is not authenticated", ApiResponseCode.UNAUTHORIZED);
        }

        String email = authentication.getName();
        User authenticatedUser = userService.findByEmail(email);
        Long authenticatedUserId = authenticatedUser.getId();

        if (!id.equals(authenticatedUserId)) {
            return ApiResponse.error("Unauthorized: Cannot remove seller from blacklist for another user", ApiResponseCode.FORBIDDEN);
        }

        try {
            userService.removeSellerFromBlacklist(id, sellerId);
            return ApiResponse.success("Seller removed from blacklist successfully.");
        } catch (RuntimeException e) {
            return ApiResponse.error(e.getMessage(), ApiResponseCode.NOT_FOUND);
        } catch (Exception e) {
            return ApiResponse.error("An error occurred while removing the seller from the blacklist", ApiResponseCode.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/blacklisted-sellers")
    @Operation(summary = "Get blacklisted sellers", description = "Get the list of sellers blacklisted by the current user.")
    public ApiResponse<?> getBlacklistedSellers() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return ApiResponse.error("User is not authenticated", ApiResponseCode.UNAUTHORIZED);
        }

        String email = authentication.getName();
        UserDto user = UserMapper.mapEntityToDto(userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found for email: " + email)));

        Set<BlackListedSellerDto> blackListedSellersDto = user.getBlackListedSellers();
        return ApiResponse.success(blackListedSellersDto, "Blacklisted sellers retrieved successfully.");
    }

}
