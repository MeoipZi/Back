package MeoipZi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import lombok.*;

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
    LocalDateTime createDateTime;


    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "parent_id")
    private CommentCommunity parentComment; //부모 댓글 나타내는 필드

    @OneToMany(mappedBy = "parentComment")
    private List<CommentCommunity> children = new ArrayList<>();



    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="community_id")
    private Community community;

    @Builder
    public CommentCommunity(String content, LocalDateTime createDateTime,User user, Community community){
        this.content = content;
        this.createDateTime = createDateTime;
        this.user = user;
        this.community = community;
    }
}
