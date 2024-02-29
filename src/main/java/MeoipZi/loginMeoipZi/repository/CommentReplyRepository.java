package MeoipZi.loginMeoipZi.repository;

import MeoipZi.loginMeoipZi.domain.CommentOutfit;
import MeoipZi.loginMeoipZi.domain.CommentReply;
import MeoipZi.loginMeoipZi.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentReplyRepository extends JpaRepository<CommentReply, Long> {
    List<CommentReply> findTop3ByUserOrderByCreatedAtDesc(User user);
}
