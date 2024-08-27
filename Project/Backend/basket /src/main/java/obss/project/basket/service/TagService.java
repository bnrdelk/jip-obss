package obss.project.basket.service;

import obss.project.basket.dto.TagUsageDto;
import obss.project.basket.entity.Tag;

import java.util.List;

public interface TagService {
    List<Tag> getAllTags();

    List<TagUsageDto> getTagUsageStatistics();
}
