package MeoipZi.loginMeoipZi.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "CMTOUTFIT")
public class CommentOutfit {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name="cmtoutfit_id")
    private Long id;

    String content;
    LocalDateTime createdAt;

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
