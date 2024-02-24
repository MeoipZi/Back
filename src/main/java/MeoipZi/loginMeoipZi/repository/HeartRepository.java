package MeoipZi.loginMeoipZi.repository;

import MeoipZi.loginMeoipZi.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HeartRepository extends JpaRepository<Heart, Long> {
    Optional<Heart> findByUserAndShortForm(User user, ShortForm shortForm);
    Optional<Heart> findByUserAndCommunity(User user, Community community);
    Optional<Heart> findByUserAndOutfit(User user, Outfit outfit);
}
