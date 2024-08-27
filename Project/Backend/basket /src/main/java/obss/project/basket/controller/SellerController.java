package obss.project.basket.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import obss.project.basket.constant.ApiResponseCode;
import obss.project.basket.dto.ApiResponse;
import obss.project.basket.dto.ProductDto;
import obss.project.basket.dto.SellerDto;
import obss.project.basket.repository.ProductRepository;
import obss.project.basket.repository.projection.ProductProjection;
import obss.project.basket.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Validated
@RestController
@Tag(name = "Seller Management", description = "Endpoints for managing sellers")
@RequestMapping("/v1/sellers")
public class SellerController {

    private final SellerService sellerService;
    private final HttpServletResponse httpServletResponse;
    private final ProductRepository productRepository;

    @Autowired
    public SellerController(SellerService sellerService, HttpServletResponse httpServletResponse, ProductRepository productRepository) {
        this.sellerService = sellerService;
        this.httpServletResponse = httpServletResponse;
        this.productRepository = productRepository;
    }

    @GetMapping
    @Operation(summary = "Get all sellers", description = "Get all sellers in the system.")
    public ApiResponse<Collection<SellerDto>> getAllSellers() {
        return ApiResponse.success(sellerService.findAllSellers(), "Sellers listed successfully.");
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a seller", description = "Get the seller in the system by given ID.")
    public ApiResponse<?> getSellerById(@PathVariable Long id) {
        SellerDto sellerDto = sellerService.findById(id);
        if (sellerDto != null) {
            return ApiResponse.success(sellerDto, "Seller listed successfully.");
        } else {
            return ApiResponse.error("Seller not found", ApiResponseCode.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/get-products")
    @Operation(summary = "Get a seller", description = "Get the seller in the system by given ID.")
    public ApiResponse<?> getProductsById(@PathVariable Long id) {
        List<ProductProjection> result = productRepository.findBySellerId(id);
        if (result != null) {
            return ApiResponse.success(result, "Seller listed successfully.");
        } else {
            return ApiResponse.error("Seller not found", ApiResponseCode.NOT_FOUND);
        }
    }
}
