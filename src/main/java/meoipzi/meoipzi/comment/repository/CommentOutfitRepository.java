package meoipzi.meoipzi.comment.repository;


import meoipzi.meoipzi.comment.domain.CommentOutfit;
import meoipzi.meoipzi.login.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentOutfitRepository extends JpaRepository<CommentOutfit, Long> {
    List<CommentOutfit> findByOutfitId(Long outfitId);
    List<CommentOutfit> findTop3ByUserOrderByCreatedAtDesc(User user);
    List<CommentOutfit> findAllByUserOrderByCreatedAtDesc(User user);
}