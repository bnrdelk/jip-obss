package obss.project.basket.repository;

import obss.project.basket.entity.Tag;
import obss.project.basket.entity.TagType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByTagType(TagType tagType);
}
