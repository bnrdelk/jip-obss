package obss.project.basket.service;

import obss.project.basket.dto.ProductDto;
import obss.project.basket.entity.Product;
import obss.project.basket.repository.projection.ProductProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface ProductService {
    List<ProductDto> findAllProductsExcludingBlacklisted(Long userId, Page<ProductDto> productDtos);
    Page<ProductDto> searchProductsWithoutBlacklist(Set<String> tagTypes, String name, Pageable pageable);
    Page<ProductDto> searchProducts(Set<String> tagTypes, String name, Pageable pageable, Long userId);
    Collection<ProductDto> getFavoriteProducts(Long userId);
    Collection<ProductDto> findAllProductsWithUserLikeStatus(Long userId);
    List<ProductDto> findAllProducts();
    @Transactional
    ProductDto createProduct(ProductDto productDto, String email);
    ProductDto findById(Long id);
    ProductDto updateProduct(ProductDto productDto, Long id);
    boolean deleteProduct(Long id);
    boolean likeProduct(Long productId, Long userId);
    boolean unlikeProduct(Long productId, Long userId);
}
