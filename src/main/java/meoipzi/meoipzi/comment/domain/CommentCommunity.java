package meoipzi.meoipzi.comment.domain;

import jakarta.persistence.*;
import lombok.*;
import meoipzi.meoipzi.domain.Community;
//import meoipzi.meoipzi.domain.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
    private CommentCommunity parent;

    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    private List<CommentCommunity> children = new ArrayList<>();



    //@ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="user_id")
    //private User user;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="community_id")
    private Community community;

    @Builder
    public CommentCommunity(String content, LocalDateTime createDateTime){
        this.content = content;
        this.createDateTime = createDateTime;
    }
}
