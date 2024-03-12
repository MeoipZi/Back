package meoipzi.meoipzi.community.domain;



import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import meoipzi.meoipzi.comment.domain.CommentCommunity;
import meoipzi.meoipzi.common.BaseEntity;
import meoipzi.meoipzi.heart.domain.Heart;
import meoipzi.meoipzi.login.domain.User;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "COMMUNITY")
@Getter @Setter
@NoArgsConstructor
public class Community extends BaseEntity {

    @GeneratedValue
    @Id
    @Column(name = "COMMUNITY_ID", updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST) // 다대일 단방향 관계, user 삭제되면 일기도 삭제
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CONTENTS")
    private String contents;

    @Column(name = "IMG_URL")
    private String imgUrl;

    @Column(name = "CATEGORY")
    private String category;

    @Column(name = "ANONYMOUS")
    private boolean isAnonymous; // 익명 여부

    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL)
    private List<Heart> commLikes = new ArrayList<>();

    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL)
    private List<CommentCommunity> commentCommunities = new ArrayList<>();

    @Column(name = "LIKES_COUNT")
    private int likesCount; // 좋아요 개수

    @Column(name = "CMT_COUNT")
    private int commentsCount; // 댓글 개수

    @Builder
    public Community(String imgUrl, User user, boolean isAnonymous,
                     String title, String contents, String category) {
        this.user = user;
        this.imgUrl = imgUrl;
        this.title = title;
        this.isAnonymous = isAnonymous;
        this.contents = contents;
        this.category = category;
        this.createdAt = getCreatedAt();
        this.formattedCreatedAt = getFormattedCreatedAt();
    }

    // 엔티티 수정 메서드
    public void update(String title, String contents, String imgUrl) {
        this.title = title;
        this.contents = contents;
        this.imgUrl = imgUrl;
    }
}