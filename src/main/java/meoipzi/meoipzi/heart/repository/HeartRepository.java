package meoipzi.meoipzi.heart.repository;

import meoipzi.meoipzi.community.domain.Community;
import meoipzi.meoipzi.heart.domain.Heart;
import meoipzi.meoipzi.login.domain.User;
import meoipzi.meoipzi.outfit.domain.Outfit;
import meoipzi.meoipzi.shortform.domain.ShortForm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HeartRepository extends JpaRepository<Heart, Long> {
    Optional<Heart> findByUserAndShortForm(User user, ShortForm shortForm);
    Optional<Heart> findByUserAndCommunity(User user, Community community);
    Optional<Heart> findByUserAndOutfit(User user, Outfit outfit);

    List<Heart> findTop3ByUserAndOutfitNotNullOrderByCreatedAtDesc(User user);
    List<Heart> findTop3ByUserAndCommunityNotNullOrderByCreatedAtDesc(User user);
    List<Heart> findTop3ByUserAndShortFormNotNullOrderByCreatedAtDesc(User user);
}