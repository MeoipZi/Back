package meoipzi.meoipzi.comment.repository;

import meoipzi.meoipzi.comment.domain.CommentOutfit;
import meoipzi.meoipzi.comment.domain.CommentShortForm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentShortFormRepository extends JpaRepository<CommentShortForm, Long> {
    List<CommentShortForm> findByShortFormId(Long shortformId);
}