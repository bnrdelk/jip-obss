package obss.project.basket.controller;

import io.swagger.v3.oas.annotations.Operation;
import obss.project.basket.constant.ApiResponseCode;
import obss.project.basket.dto.ApiResponse;
import obss.project.basket.dto.TagUsageDto;
import obss.project.basket.entity.Tag;
import obss.project.basket.service.ProductService;
import obss.project.basket.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/v1/tags")
public class TagController {

    private final TagService tagService;
    private final ProductService productService;

    @Autowired
    public TagController(TagService tagService, ProductService productService) {
        this.tagService = tagService;
        this.productService = productService;
    }

    @GetMapping
    @Operation(summary = "Get all tags", description = "Retrieve all tags from the system.")
    public ApiResponse<List<Tag>> getAllTags() {
        List<Tag> tags = tagService.getAllTags();
        return ApiResponse.success(tags, "Tags retrieved successfully.");
    }

    @GetMapping("/usage")
    @Operation(summary = "Get tag usage statistics", description = "Retrieve usage statistics for tags across all products.")
    public ApiResponse<List<TagUsageDto>> getTagUsageStatistics() {
        List<TagUsageDto> tagUsageStatistics = tagService.getTagUsageStatistics();
        return ApiResponse.success(tagUsageStatistics, "Tag usage statistics retrieved successfully.");
    }
}
