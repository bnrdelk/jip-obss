package obss.project.basket.mapper;

import obss.project.basket.dto.*;
import obss.project.basket.entity.Seller;
import obss.project.basket.entity.User;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Set;
import java.util.stream.Collectors;

public class UserMapper {
    public static User mapInputDtoToEntity(UserInputDto userInputDto) {
        User user = new User();
        user.setEmail(userInputDto.getEmail());
        user.setPassword(new String(Base64.getEncoder().encode(
                userInputDto.getPassword().getBytes(StandardCharsets.UTF_8)
        )));
        user.setName(userInputDto.getName());
        user.setSurname(userInputDto.getSurname());
        user.setEmail(userInputDto.getSurname());
        return user;
    }

    public static UserInputDto mapEntityToInputDto(User user) {
        UserInputDto userInputDto = new UserInputDto();
        userInputDto.setEmail(user.getEmail());
        userInputDto.setName(user.getName());
        userInputDto.setSurname(user.getSurname());
        userInputDto.setPassword(user.getPassword());

        return userInputDto;
    }

    public static BlackListedSellerDto mapEntityToDto(Seller seller) {
        BlackListedSellerDto dto = new BlackListedSellerDto();
        dto.setId(seller.getId());
        dto.setFullName(seller.getName() + " " + seller.getSurname());
        dto.setEmail(seller.getEmail());
        return dto;
    }

    public static UserDto mapEntityToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setRoles(user.getRoles());
        userDto.setId(user.getId());
        userDto.setFullName(user.getName() + " " + user.getSurname());

        Set<ProductDto> favoriteProductsDto = user.getFavoriteProducts().stream()
                .map(ProductMapper::mapEntityToDto)
                .collect(Collectors.toSet());

        Set<BlackListedSellerDto> blackListedSellersDto = user.getBlackListedSellers().stream()
                .map(UserMapper::mapEntityToDto) // Ensure the mapping method matches the new DTO
                .collect(Collectors.toSet());

        userDto.setFavoriteProducts(favoriteProductsDto);
        userDto.setBlackListedSellers(blackListedSellersDto);
        return userDto;
    }

}
