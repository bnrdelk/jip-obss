package obss.project.basket.service.Impl;

import obss.project.basket.dto.SellerDto;
import obss.project.basket.entity.Seller;
import obss.project.basket.mapper.SellerMapper;
import obss.project.basket.repository.SellerRepository;
import obss.project.basket.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SellerServiceImpl implements SellerService {

    private final SellerRepository sellerRepository;

    @Autowired
    public SellerServiceImpl(SellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }

    @Override
    public List<SellerDto> findAllSellers() {
        List<Seller> sellers = sellerRepository.findAll();
        return sellers.stream()
                .map(SellerMapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public SellerDto findById(Long id) {
        Seller seller = sellerRepository.findById(id).orElse(null);
        if (seller != null) {
            return SellerMapper.mapEntityToDto(seller);
        }
        return null;
    }

}
