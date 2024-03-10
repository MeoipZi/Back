package meoipzi.meoipzi.comment.domain;

import jakarta.persistence.*;
import lombok.*;
import meoipzi.meoipzi.community.domain.Community;
import meoipzi.meoipzi.login.domain.User;
import org.springframework.data.annotation.CreatedDate;
//import meoipzi.meoipzi.domain.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "commentCommunities")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentCommunity {
    @GeneratedValue
    @Id
    @Column(name="comment_community_id")
    private Long id;

    @Column(nullable = false, length = 1000)
    String content;
    @Column(name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;


    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "parent_id")
    private CommentCommunity parentComment;//부모 댓글 나타내는 필드

    @OneToMany(mappedBy = "parentComment")
    private List<CommentCommunity> children = new ArrayList<>();



    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="community_id")
    private Community community;

    @Builder
    public CommentCommunity(String content, LocalDateTime createdAt,User user, Community community){
        this.content = content;
        this.createdAt = createdAt;
        this.user = user;
        this.community = community;
    }
}
