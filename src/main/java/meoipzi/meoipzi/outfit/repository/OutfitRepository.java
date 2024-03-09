package meoipzi.meoipzi.outfit.repository;

import meoipzi.meoipzi.comment.domain.CommentOutfit;
import meoipzi.meoipzi.outfit.domain.Outfit;
import meoipzi.meoipzi.outfit.dto.OutfitTotalResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OutfitRepository extends JpaRepository<Outfit, Long> {
    Page<Outfit> findAllByOrderByIdDesc(Pageable pageable);
    Page<Outfit> findByOrderByLikesCountDesc(Pageable pageable);

}