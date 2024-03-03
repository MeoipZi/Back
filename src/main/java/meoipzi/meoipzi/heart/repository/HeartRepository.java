package meoipzi.meoipzi.heart.repository;

import meoipzi.meoipzi.domain.Community;
import meoipzi.meoipzi.heart.domain.Heart;
import meoipzi.meoipzi.login.domain.User;
import meoipzi.meoipzi.outfit.domain.Outfit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HeartRepository extends JpaRepository<Heart, Long> {
    //Optional<Heart> findByUserAndShortForm(User user, ShortForm shortForm);
    Optional<Heart> findByUserAndCommunity(User user, Community community);
    Optional<Heart> findByUserAndOutfit(User user, Outfit outfit);

    //List<Heart> findTop3ByUserOutfitNotNullOrderByCreatedAtDesc(User user);
    //List<Heart> findTop3ByUserCommunityNotNullOrderByCreatedAtDesc(User user);
    //List<Heart> findTop3ByUserShortFormNotNullOrderByCreatedAtDesc(User user);
}