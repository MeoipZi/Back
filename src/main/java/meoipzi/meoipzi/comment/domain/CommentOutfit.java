package meoipzi.meoipzi.comment.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import meoipzi.meoipzi.common.BaseTimeEntity;
import meoipzi.meoipzi.login.domain.User;
import meoipzi.meoipzi.outfit.domain.Outfit;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "CMTOUTFIT")
@NoArgsConstructor
public class CommentOutfit extends BaseTimeEntity {
    @GeneratedValue
    @Id
    @Column(name="comment_outfit_id")
    private Long id;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="outfit_id")
    private Outfit outfit;


    @Builder
    public CommentOutfit(String content, User user,Outfit outfit){
        this.content = content;
        this.user = user;
        this.outfit = outfit;
    }
}
