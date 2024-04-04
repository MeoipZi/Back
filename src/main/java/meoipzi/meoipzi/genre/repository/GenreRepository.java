package meoipzi.meoipzi.genre.repository;

import meoipzi.meoipzi.genre.domain.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    Genre findGenreById(Long genreId);
}
