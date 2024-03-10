package MeoipZi.repository.CommentRepository;

import MeoipZi.domain.CommentCommunity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentCommunityRepository extends JpaRepository<CommentCommunity, Long> {
    List<CommentCommunity> findByCommunityId(Long communityId);
}
