package obss.project.basket.service.Impl;

import obss.project.basket.dto.TagUsageDto;
import obss.project.basket.entity.Product;
import obss.project.basket.entity.Tag;
import obss.project.basket.repository.ProductRepository;
import obss.project.basket.repository.TagRepository;
import obss.project.basket.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final ProductRepository productRepository;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository, ProductRepository productRepository) {
        this.tagRepository = tagRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    @Override
    public List<TagUsageDto> getTagUsageStatistics() {
        List<Product> products = productRepository.findAll();

        Map<String, Integer> tagUsageMap = new HashMap<>();
        for (Product product : products) {
            for (Tag tag : product.getTags()) {
                tagUsageMap.put(tag.getTagType().name(), tagUsageMap.getOrDefault(tag.getTagType().name(), 0) + 1);
            }
        }

        return tagUsageMap.entrySet().stream()
                .map(entry -> new TagUsageDto(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

}


