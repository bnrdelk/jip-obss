package obss.project.basket.mapper;

import obss.project.basket.dto.SellerDto;
import obss.project.basket.entity.Seller;

import java.time.LocalDateTime;

public class SellerMapper {

    public static Seller mapDtoToEntity(SellerDto sellerDto) {
        Seller seller = new Seller();
        seller.setUpdatedAt(LocalDateTime.now());
        seller.setCreatedAt(LocalDateTime.now());
        seller.setId(sellerDto.getId());
        seller.setName(sellerDto.getName());
        seller.setSurname(sellerDto.getSurname());
        seller.setEmail(sellerDto.getEmail());
        return seller;
    }

    public static SellerDto mapEntityToDto(Seller seller) {
        SellerDto sellerDto = new SellerDto();
        sellerDto.setId(seller.getId());
        sellerDto.setName(seller.getName());
        sellerDto.setSurname(seller.getSurname());
        sellerDto.setEmail(seller.getEmail());
        return sellerDto;
    }
}
