/*package meoipzi.meoipzi.genre.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import meoipzi.meoipzi.genreoutfit.GenreOutfit;

import java.util.ArrayList;
import java.util.List;
*//*
@Entity
@Table(name = "GENRE")
@Getter
@Setter
@NoArgsConstructor
public class Genre {

    @Id
    @GeneratedValue
    @Column(name = "genre_id")
    private Long id;

    @Column(name = "name")
    private String name;

    // GenreOutfit 엔티티와의 양방향 관계 설정
    @OneToMany(mappedBy = "genre", cascade = CascadeType.ALL)
    private List<GenreOutfit> genreOutfits = new ArrayList<>();

    public Genre(String name) {
        this.name = name;
    }
}
*/