package obss.project.basket.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import obss.project.basket.constant.ApiResponseCode;
import obss.project.basket.dto.ApiResponse;
import obss.project.basket.dto.ProductDto;
import obss.project.basket.dto.UserDto;
import obss.project.basket.entity.Seller;
import obss.project.basket.entity.User;
import obss.project.basket.mapper.SellerMapper;
import obss.project.basket.mapper.UserMapper;
import obss.project.basket.repository.ProductRepository;
import obss.project.basket.repository.SellerRepository;
import obss.project.basket.repository.UserRepository;
import obss.project.basket.service.ProductService;
import obss.project.basket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Slf4j
@Validated
@RestController
@Tag(name = "Product Management", description = "Endpoints for managing products")
@RequestMapping("/v1/products")
public class ProductController {

    private final ProductService productService;
    private final UserService userService;
    private final HttpServletResponse httpServletResponse;
    private final SellerRepository sellerRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Autowired
    public ProductController(ProductService productService, UserService userService, HttpServletResponse httpServletResponse, SellerRepository sellerRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.productService = productService;
        this.userService = userService;
        this.httpServletResponse = httpServletResponse;
        this.sellerRepository = sellerRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @PostMapping("/add")
    @Operation(summary = "Create a new product", description = "Add a new product to the system.")
    public ApiResponse<?> addProduct(@RequestBody @Validated ProductDto productDto, @RequestParam String email) {

        Optional<Seller> sellerOptional = sellerRepository.findByEmail(email);

        if (sellerOptional.isPresent()) {
            productDto.setSeller(SellerMapper.mapEntityToDto(sellerOptional.get()));
        } else {
            throw new RuntimeException("Seller not found for email: " + email);
        }
        log.info("Received product DTO: {}", productDto);
        ProductDto createdProduct = productService.createProduct(productDto, email);
        createdProduct.setSeller(SellerMapper.mapEntityToDto(sellerOptional.get()));
        return ApiResponse.success(createdProduct, "Product created successfully.");
    }

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = file.getOriginalFilename();
        Path filePath = Paths.get("src/main/resources/static/uploads", fileName);

        try {
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            String fileUrl = "/upload/" + fileName;

            Map<String, String> response = new HashMap<>();
            response.put("url", fileUrl);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/user/favorites/{id}")
    @Operation(summary = "Get all favorite products of the authenticated user", description = "Retrieve all favorite products for the currently authenticated user.")
    public ApiResponse<Collection<ProductDto>> getFavoriteProducts(@PathVariable Long id) {
        UserDto user = userService.findById(id);
        if (user == null) {
            return ApiResponse.error("User not found", ApiResponseCode.NOT_FOUND);
        }
        return ApiResponse.success(productService.getFavoriteProducts(user.getId()), "Favorite products retrieved successfully.");
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Update a product", description = "Update the product in the system.")
    public ApiResponse<?> updateProduct(@RequestBody @Validated ProductDto productDto, @PathVariable Long id) {
        ProductDto updatedProduct = productService.updateProduct(productDto, id);
        if (updatedProduct != null) {
            return ApiResponse.success(updatedProduct, "Product updated successfully.");
        } else {
            return ApiResponse.error("Product not found", ApiResponseCode.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete a product", description = "Delete the product in the system.")
    public ApiResponse<?> deleteProduct(@PathVariable Long id) {
        boolean isDeleted = productService.deleteProduct(id);
        if (isDeleted) {
            return ApiResponse.success("Product deleted successfully.");
        } else {
            return ApiResponse.error("Product not found", ApiResponseCode.NOT_FOUND);
        }
    }

    @GetMapping
    @Operation(summary = "Get all products", description = "Get all products in the system.")
    public ApiResponse<Collection<ProductDto>> getAllProducts() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            return ApiResponse.success(productService.findAllProducts(), "Products listed successfully.");
        } else {
            String email = authentication.getName();
            User user = userService.findByEmail(email);
            return ApiResponse.success(productService.findAllProductsWithUserLikeStatus(user.getId()), "Products listed successfully.");
        }
    }


    @GetMapping("/{id}")
    @Operation(summary = "Get a product", description = "Get the product in the system by given ID.")
    public ApiResponse<?> getProductById(@PathVariable Long id) {
        ProductDto productDto = productService.findById(id);
        if (productDto != null) {
            return ApiResponse.success(productDto, "Product listed successfully.");
        } else {
            return ApiResponse.error("Product not found", ApiResponseCode.NOT_FOUND);
        }
    }

    @PostMapping("/like/{id}")
    @Operation(summary = "Like a product", description = "Allows a user to like a product.")
    public ApiResponse<?> likeProduct(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            return ApiResponse.error("User is not authenticated", ApiResponseCode.UNAUTHORIZED);
        }

        String email = authentication.getName();
        User user = userService.findByEmail(email);

        if (user == null) {
            return ApiResponse.error("User not found", ApiResponseCode.NOT_FOUND);
        }

        boolean isLiked = productService.likeProduct(id, user.getId());
        if (isLiked) {
            return ApiResponse.success("Product liked successfully.");
        } else {
            return ApiResponse.error("Product already liked.", ApiResponseCode.CONFLICT);
        }
    }

    @PostMapping("/unlike/{id}")
    @Operation(summary = "Unlike a product", description = "Allows a user to unlike a product.")
    public ApiResponse<?> unlikeProduct(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()|| authentication.getPrincipal().equals("anonymousUser")) {
            return ApiResponse.error("User is not authenticated", ApiResponseCode.UNAUTHORIZED);
        }

        String email = authentication.getName();
        UserDto userDto = UserMapper.mapEntityToDto(userService.findByEmail(email));

        boolean isUnliked = productService.unlikeProduct(id, userDto.getId());
        if (isUnliked) {
            return ApiResponse.success("Product unliked successfully.");
        } else {
            return ApiResponse.error("Product not found", ApiResponseCode.NOT_FOUND);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ProductDto>> searchProducts(
            @RequestParam(value = "tagTypes", required = false) Set<String> tagTypes,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Page<ProductDto> productDtoPage = productService.searchProductsWithoutBlacklist(tagTypes, name, pageable);

        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            return ResponseEntity.ok(productDtoPage);
        } else {
            String email = authentication.getName();
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            List<ProductDto> filtered = productService.findAllProductsExcludingBlacklisted(user.getId(), productDtoPage);
            Page<ProductDto> filteredPage = new PageImpl<>(filtered);

            return ResponseEntity.ok(filteredPage);
        }
    }

}

