package MeoipZi.loginMeoipZi.repository;

import MeoipZi.loginMeoipZi.domain.CommentOutfit;
import MeoipZi.loginMeoipZi.domain.CommentShortForm;
import MeoipZi.loginMeoipZi.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentShortFormRepository extends JpaRepository<CommentShortForm, Long> {
    List<CommentShortForm> findTop3ByUserOrderByCreatedAtDesc(User user);
    List<CommentShortForm> findByShortFormId(Long shortformId);
}
