package meoipzi.meoipzi.comment.repository;


import meoipzi.meoipzi.comment.domain.CommentCommunity;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CommentCommunityRepository extends JpaRepository<CommentCommunity, Long> {
}
