package obss.project.basket.service;

import obss.project.basket.dto.SellerDto;

import java.util.List;

public interface SellerService {
    List<SellerDto> findAllSellers();
    SellerDto findById(Long id);
}
