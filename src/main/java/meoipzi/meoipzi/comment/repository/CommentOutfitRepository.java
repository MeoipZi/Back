package meoipzi.meoipzi.comment.repository;


import meoipzi.meoipzi.comment.domain.CommentOutfit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentOutfitRepository extends JpaRepository<CommentOutfit, Long> {
    List<CommentOutfit> findByOutfitId(Long outfitId);
}