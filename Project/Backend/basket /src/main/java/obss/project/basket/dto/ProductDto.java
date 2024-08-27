package obss.project.basket.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import obss.project.basket.entity.Seller;

import java.util.Set;
@Data
@NoArgsConstructor
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String image;
    private SellerDto seller;
    private boolean likedByUser;
    private Long likeCount;
    private Set<String> tags;

    public ProductDto(Long id, String name, String description, Double price, String image, SellerDto seller, boolean likedByUser, Set<String> tags) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
        this.seller = seller;
        this.likedByUser = likedByUser;
        this.tags = tags;
    }
}