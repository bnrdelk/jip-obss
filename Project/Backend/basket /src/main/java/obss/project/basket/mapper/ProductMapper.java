package obss.project.basket.mapper;

import obss.project.basket.dto.ProductDto;
import obss.project.basket.dto.SellerDto;
import obss.project.basket.entity.Product;
import obss.project.basket.entity.Tag;
import obss.project.basket.repository.projection.ProductProjection;
import obss.project.basket.service.UserService;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public class ProductMapper {
    public static Product mapDtoToEntity(ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setImage(productDto.getImage());
        product.setSeller(SellerMapper.mapDtoToEntity(productDto.getSeller()));
        product.setUpdatedAt(LocalDateTime.now());
        product.setCreatedAt(LocalDateTime.now());
        return product;
    }

    public static ProductDto mapEntityToDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setLikedByUser(false);
        productDto.setLikeCount(product.getLikedByUsers().stream().count());
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setTags(product.getTags().stream()
                .map(tag -> tag.getTagType().name())
                .collect(Collectors.toSet()));
        productDto.setSeller(SellerMapper.mapEntityToDto(product.getSeller()));
        productDto.setImage(product.getImage());
        return productDto;
    }

}