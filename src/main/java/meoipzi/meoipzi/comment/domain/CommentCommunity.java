package meoipzi.meoipzi.comment.domain;

import jakarta.persistence.*;
import lombok.*;
import meoipzi.meoipzi.common.BaseTimeEntity;
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
@Table(name = "CMTCOMM")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentCommunity extends BaseTimeEntity {
    @GeneratedValue
    @Id
    @Column(name="comment_community_id")
    private Long id;

    @Column(nullable = false, length = 1000)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "parent_id")
    private CommentCommunity parentComment;//부모 댓글 나타내는 필드

    @OneToMany(mappedBy = "parentComment")
    private List<CommentCommunity> children = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="community_id")
    private Community community;

    @Builder
    public CommentCommunity(String content,User user, Community community){
        this.content = content;
        this.user = user;
        this.community = community;
    }
}
