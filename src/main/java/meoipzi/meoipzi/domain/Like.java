package meoipzi.meoipzi.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import meoipzi.meoipzi.outfit.domain.Outfit;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "likes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Like {
    @GeneratedValue
    @Id
    @Column(name="like_id")
    private Long id;
    private int category;
    private LocalDateTime createDateTime;

    //@ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="user_id")
   // private User user;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="outfit_id")
    private Outfit outfit;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="shortForm_id")
    private ShortForm shortForm;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="community_id")
    private Community community;
}
