package meoipzi.meoipzi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import meoipzi.meoipzi.outfit.domain.Outfit;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "commentOutfits")
public class CommentOutfit {
    @GeneratedValue
    @Id
    @Column(name="comment_outfit_id")
    private Long id;
    String content;
    LocalDateTime createDateTime;

    //@ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="user_id")
    //private User user;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="outfit_id")
    private Outfit outfit;
}
