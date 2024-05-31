package meoipzi.meoipzi.comment.repository;


import meoipzi.meoipzi.comment.domain.CommentCommunity;
import meoipzi.meoipzi.login.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentCommunityRepository extends JpaRepository<CommentCommunity, Long> {
    @Query("SELECT c FROM CommentCommunity c " +
            "WHERE c.user = :user AND c.id IN ( " +
            "  SELECT MAX(c2.id) FROM CommentCommunity c2 " +
            "  WHERE c2.user = :user " +
            "  GROUP BY c2.community.id " +
            ") " +
            "ORDER BY c.createdAt DESC")
    List<CommentCommunity> findTop3DistinctCommentsByUserOrderByCreatedAtDesc(@Param("user") User user, Pageable pageable);

    @Query("SELECT c FROM CommentCommunity c " +
            "WHERE c.user = :user AND c.id IN ( " +
            "  SELECT MAX(c2.id) FROM CommentCommunity c2 " +
            "  WHERE c2.user = :user " +
            "  GROUP BY c2.community.id " +
            ") " +
            "ORDER BY c.createdAt DESC")
    List<CommentCommunity> findDistinctCommentsByUserOrderByCreatedAtDesc(@Param("user") User user);

    List<CommentCommunity> findByCommunityId(Long communityId);

    // 부모 댓글을 기준으로 대댓글 조회
    List<CommentCommunity> findByParentComment(CommentCommunity parentComment);
}