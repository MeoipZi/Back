package meoipzi.meoipzi.outfit.repository;

import meoipzi.meoipzi.outfit.domain.Outfit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutfitRepository extends JpaRepository<Outfit, Long> {
    Page<Outfit> findAllByOrderByIdDesc(Pageable pageable);
    Page<Outfit> findByOrderByLikesCountDesc(Pageable pageable);

}