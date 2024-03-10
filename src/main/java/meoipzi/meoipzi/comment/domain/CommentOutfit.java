package meoipzi.meoipzi.comment.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import meoipzi.meoipzi.login.domain.User;
import meoipzi.meoipzi.profile.outfit.domain.Outfit;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "commentOutfits")
@NoArgsConstructor
public class CommentOutfit {
    @GeneratedValue
    @Id
    @Column(name="comment_outfit_id")
    private Long id;
    private String content;
    @Column(name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="outfit_id")
    private Outfit outfit;


    @Builder
    public CommentOutfit(String content, LocalDateTime createdAt, User user,Outfit outfit){
        this.content = content;
        this.createdAt = createdAt;
        this.user = user;
        this.outfit = outfit;
    }
}
