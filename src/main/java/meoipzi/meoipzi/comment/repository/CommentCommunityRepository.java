package meoipzi.meoipzi.comment.repository;


import meoipzi.meoipzi.comment.domain.CommentCommunity;
import meoipzi.meoipzi.login.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentCommunityRepository extends JpaRepository<CommentCommunity, Long> {
    @Query("SELECT DISTINCT c FROM CommentCommunity c WHERE c.community NOT IN " +
            "(SELECT c2.community FROM CommentCommunity c2 WHERE c2.user = ?1 AND c2.id <> c.id) " +
            "ORDER BY c.createdAt DESC")
    List<CommentCommunity> findTop3DistinctCommentsByUserOrderByCreatedAtDesc(User user, Pageable pageable);


    @Query("SELECT DISTINCT c FROM CommentCommunity c " +
            "WHERE c.user = ?1 AND c.community NOT IN " +
            "(SELECT c2.community FROM CommentCommunity c2 WHERE c2.user = ?1 AND c2.id <> c.id) " +
            "ORDER BY c.createdAt DESC")
    List<CommentCommunity> findDistinctCommentsByUserOrderByCreatedAtDesc(User user);


    List<CommentCommunity> findByCommunityId(Long communityId);

    // 부모 댓글을 기준으로 대댓글 조회
    List<CommentCommunity> findByParentComment(CommentCommunity parentComment);
}
