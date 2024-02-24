package MeoipZi.meoipzi.repository;

import MeoipZi.meoipzi.domain.CommentReply;
import MeoipZi.meoipzi.domain.Shortform;
import com.amazonaws.services.ec2.model.ClientVpnConnection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShortformRepository extends JpaRepository<Shortform, Long> {
    //Optional<Shortform> findByIdAndUserId(Long cmtId, Long userId);
    //List<Shortform> findAllOrderByCreated_atDesc(); // 최신순 조회
    //List<Shortform> findAllOrderByLikesCountDesc(); // 좋아요순 조회
}
