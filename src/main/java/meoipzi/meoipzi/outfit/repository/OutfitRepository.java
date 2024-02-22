package meoipzi.meoipzi.outfit.repository;

import meoipzi.meoipzi.outfit.domain.Outfit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OutfitRepository extends JpaRepository<Outfit, Long> {

}