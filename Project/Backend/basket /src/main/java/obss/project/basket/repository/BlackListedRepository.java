package obss.project.basket.repository;

import obss.project.basket.entity.Blacklist;
import obss.project.basket.entity.Product;
import obss.project.basket.entity.Seller;
import obss.project.basket.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface BlackListedRepository extends JpaRepository<Blacklist, Long> {

    @Query("SELECT b.seller.id FROM Blacklist b WHERE b.user.id = :userId")
    Set<Long> findBlacklistedSellerIdsByUser(@Param("userId") Long userId);

    Optional<Blacklist> findByUserAndSeller(User user, Seller seller);
}
