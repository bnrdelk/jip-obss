package obss.project.basket.service.Impl;

import obss.project.basket.dto.ProductDto;
import obss.project.basket.dto.TagUsageDto;
import obss.project.basket.entity.*;
import obss.project.basket.mapper.ProductMapper;
import obss.project.basket.mapper.SellerMapper;
import obss.project.basket.repository.*;
import obss.project.basket.service.ProductService;
import obss.project.basket.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final BlackListedRepository blackListedRepository;

    public ProductServiceImpl(ProductRepository productRepository, SellerRepository sellerRepository, TagRepository tagRepository, UserRepository userRepository, UserService userService, BlackListedRepository blackListedRepository) {
        this.productRepository = productRepository;
        this.sellerRepository = sellerRepository;
        this.tagRepository = tagRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.blackListedRepository = blackListedRepository;
    }


    @Override
    public List<ProductDto> findAllProductsExcludingBlacklisted(Long userId, Page<ProductDto> productDtos) {
        Set<Long> blacklistedSellerIds = blackListedRepository.findBlacklistedSellerIdsByUser(userId);

        return productDtos.stream()
                .filter(product -> !blacklistedSellerIds.contains(product.getSeller().getId()))
                .collect(Collectors.toList());
    }

    @Override
    public Page<ProductDto> searchProductsWithoutBlacklist(Set<String> tagTypes, String name, Pageable pageable) {
        Set<TagType> tagTypeEnums = null;
        if (tagTypes != null) {
            tagTypeEnums = tagTypes.stream()
                    .map(TagType::valueOf)
                    .collect(Collectors.toSet());
        }

        Page<Product> products = productRepository.searchProductsWithoutBlacklist(name, tagTypeEnums, pageable);

        return products.map(ProductMapper::mapEntityToDto);
    }


    @Override
    public Page<ProductDto> searchProducts(Set<String> tagTypes, String name, Pageable pageable, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Set<Seller> blacklistedSellers = user.getBlackListedSellers();

        Set<TagType> tagTypeEnums = null;
        if (tagTypes != null) {
            tagTypeEnums = tagTypes.stream()
                    .map(TagType::valueOf)
                    .collect(Collectors.toSet());
        }

        Page<Product> products = productRepository.searchProducts(name, tagTypeEnums, blacklistedSellers, pageable);

        return products.map(ProductMapper::mapEntityToDto);
    }


    @Override
    public Collection<ProductDto> getFavoriteProducts(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Set<Product> favoriteProducts = user.getFavoriteProducts();

        return favoriteProducts.stream()
                .map(product -> {
                    ProductDto productDto = ProductMapper.mapEntityToDto(product);
                    productDto.setLikedByUser(true);
                    productDto.setSeller(SellerMapper.mapEntityToDto(product.getSeller()));
                    return productDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Collection<ProductDto> findAllProductsWithUserLikeStatus(Long userId) {
        List<Product> products = productRepository.findAll();
        Set<Product> userFavoriteProducts = userRepository.findById(userId)
                .map(User::getFavoriteProducts)
                .orElse(Collections.emptySet());

        return products.stream()
                .map(product -> {
                    ProductDto productDto = ProductMapper.mapEntityToDto(product);
                    productDto.setLikedByUser(userFavoriteProducts.contains(product));
                    return productDto;
                })
                .collect(Collectors.toList());
    }


    @Override
    public List<ProductDto> findAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(ProductMapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProductDto createProduct(ProductDto productDto, String email) {
        Product product = new Product();
        product = ProductMapper.mapDtoToEntity(productDto);

        Seller seller = sellerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Seller not found for email: " + email));
        product.setSeller(seller);

        Set<Tag> tags = productDto.getTags().stream()
                .map(tagName -> {
                    try {
                        TagType tagType = TagType.valueOf(tagName);
                        Tag tag = tagRepository.findByTagType(tagType)
                                .orElseThrow(() -> new RuntimeException("Tag not found for tag type: " + tagName));
                        return tag;
                    } catch (IllegalArgumentException e) {
                        throw new RuntimeException("Invalid tag type: " + tagName, e);
                    }
                })
                .collect(Collectors.toSet());
        product.setTags(tags);
        product.setLikedByUsers(new HashSet<>());
        Product savedProduct = productRepository.save(product);

        return ProductMapper.mapEntityToDto(savedProduct);
    }

    @Override
    public ProductDto findById(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product != null) {
            return ProductMapper.mapEntityToDto(product);
        }
        return null;
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product != null) {
            product.setName(productDto.getName());
            product.setDescription(productDto.getDescription());
            product.setPrice(productDto.getPrice());
            productRepository.save(product);
            return ProductMapper.mapEntityToDto(product);
        }
        return null;
    }

    @Override
    public boolean deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product != null) {
            productRepository.delete(product);
            return true;
        }
        return false;
    }

    @Override
    public boolean likeProduct(Long productId, Long userId) {
        Product product = productRepository.findById(productId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);

        if (product == null || user == null) {
            return false;
        }

        if (product.getLikedByUsers() == null) {
            product.setLikedByUsers(new HashSet<>());
        }

        boolean added = product.getLikedByUsers().add(user);
        productRepository.save(product);
        return added;
    }

    @Override
    public boolean unlikeProduct(Long productId, Long userId) {
        Product product = productRepository.findById(productId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);

        if (product == null || user == null) {
            return false;
        }

        boolean removed = product.getLikedByUsers().remove(user);
        productRepository.save(product);
        return removed;
    }
}