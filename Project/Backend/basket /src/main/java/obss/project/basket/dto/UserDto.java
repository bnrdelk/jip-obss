package obss.project.basket.dto;

import lombok.Data;
import obss.project.basket.entity.Product;
import obss.project.basket.entity.Role;

import java.util.Set;

@Data
public class UserDto {
    Long id;
    String email;
    String fullName;
    Set<Role> roles;
    Set<ProductDto> favoriteProducts;
    private Set<BlackListedSellerDto> blackListedSellers;
}
