package meoipzi.meoipzi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Entity
@Getter
@Setter
@Table(name = "commentCommunities")
public class CommentCommunity {
    @GeneratedValue
    @Id
    @Column(name="comment_community_id")
    private Long id;
    String content;
    LocalDateTime createDateTime;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="community_id")
    private Community community;
}
