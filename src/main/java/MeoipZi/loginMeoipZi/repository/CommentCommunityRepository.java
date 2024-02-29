package MeoipZi.loginMeoipZi.repository;

import MeoipZi.loginMeoipZi.domain.CommentCommunity;
import MeoipZi.loginMeoipZi.domain.CommentOutfit;
import MeoipZi.loginMeoipZi.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentCommunityRepository extends JpaRepository<CommentCommunity, Long> {
    List<CommentCommunity> findTop3ByUserOrderByCreatedAtDesc(User user);
}
