/*package meoipzi.meoipzi.genreoutfit;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import meoipzi.meoipzi.genre.domain.Genre;
import meoipzi.meoipzi.outfit.domain.Outfit;
*//*
@Entity
@Table(name = "GENRE_OUTFIT")
@Getter
@Setter
@NoArgsConstructor
public class GenreOutfit {

    @Id
    @GeneratedValue
    @Column(name = "genre_outfit_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "outfit_id")
    private Outfit outfit;

    public GenreOutfit(Genre genre, Outfit outfit) {
        this.genre = genre;
        this.outfit = outfit;
    }
}
*/