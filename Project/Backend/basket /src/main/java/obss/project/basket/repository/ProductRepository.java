package obss.project.basket.repository;

import obss.project.basket.entity.*;
import obss.project.basket.repository.projection.ProductProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findById(Long id);
    List<ProductProjection> findByNameContainingIgnoreCase(String name);
    List<ProductProjection> findBySellerId(Long sellerId);
    List<ProductProjection> findByTagsTagTypeIn(List<String> tagTypes);

    @Query("SELECT DISTINCT p FROM Product p " +
            "LEFT JOIN p.tags t " +
            "LEFT JOIN p.seller s " +
            "WHERE (:name IS NULL OR p.name LIKE %:name%) " +
            "AND (:tagTypes IS NULL OR t.tagType IN :tagTypes)")
    Page<Product> searchProductsWithoutBlacklist(@Param("name") String name,
                                                 @Param("tagTypes") Set<TagType> tagTypes,
                                                 Pageable pageable);


    @Query("SELECT DISTINCT p FROM Product p " +
            "LEFT JOIN p.tags t " +
            "LEFT JOIN p.seller s " +
            "WHERE (:name IS NULL OR p.name LIKE %:name%) " +
            "AND (:tagTypes IS NULL OR t.tagType IN :tagTypes) " +
            "AND (:blacklistedSellers IS NULL OR s NOT IN :blacklistedSellers)")
    Page<Product> searchProducts(@Param("name") String name,
                                 @Param("tagTypes") Set<TagType> tagTypes,
                                 @Param("blacklistedSellers") Set<Seller> blacklistedSellers,
                                 Pageable pageable);



}