package obss.project.basket.repository.projection;

import obss.project.basket.dto.SellerDto;
import obss.project.basket.entity.Tag;

import java.util.Set;

public interface ProductProjection {
    Long getId();
    String getName();
    String getDescription();
    double getPrice();
    String getImage();
    SellerInfo getSeller();
    Set<Tag> getTags();

    interface SellerInfo {
        Long getId();
        String getName();
    }
}
