package MeoipZi.loginMeoipZi.repository;

import MeoipZi.loginMeoipZi.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {
    Optional<Scrap> findByUserAndProduct(User user, Product product);
    Optional<Scrap> findByUserAndOutfit(User user, Outfit outfit);

    List<Scrap> findTop3ByUserAndProductNotNullOrderByCreatedAtDesc(User user);
    List<Scrap> findTop3ByUserAndOutfitNotNullOrderByCreatedAtDesc(User user);
}
