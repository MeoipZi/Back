package meoipzi.meoipzi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import meoipzi.meoipzi.comment.domain.CommentCommunity;
import meoipzi.meoipzi.heart.domain.Heart;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "communities")
public class Community {
    @GeneratedValue
    @Id
    @Column(name="community_id")
    Long id;
    String title;
    String imgUrl;
    String content;
    LocalDateTime createDateTime;
    LocalDateTime updateDateTime;
    String nickname;
    //좋아요개수
    //댓글 개수
    String category;

    @OneToMany(mappedBy = "community")
    private List<Heart> likes = new ArrayList<>();

    @OneToMany(mappedBy = "community")
    private List<CommentCommunity> commentCommunities = new ArrayList<>();
}
