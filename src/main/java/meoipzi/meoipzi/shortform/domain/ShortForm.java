package meoipzi.meoipzi.shortform.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import meoipzi.meoipzi.comment.domain.CommentShortForm;
import meoipzi.meoipzi.common.BaseEntity;
import meoipzi.meoipzi.heart.domain.Heart;
import meoipzi.meoipzi.login.domain.User;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "SHORTFORM")
@Getter
@Setter
@NoArgsConstructor
public class ShortForm extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST) // 다대일 단방향 관계, user 삭제되면 숏폼도 삭제
    @JoinColumn(name = "USER_ID")   // User.java의 id와 FK 관계
    private User user;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CONTENTS")
    private String contents;

    @Column(name = "LIKES_COUNT")
    private int likesCount;

    @Column(name = "COMMENTS_COUNT")
    private int commentsCount;

    @Column(name = "IMAGE_URL")
    @NotEmpty @NotNull
    private String imgUrl; // 숏폼 게시글 imgUrl

    @OneToMany(mappedBy = "shortForm", cascade = CascadeType.ALL)
    private List<Heart> likes = new ArrayList<>();

    @OneToMany(mappedBy = "shortForm", cascade = CascadeType.ALL)
    private List<CommentShortForm> comments = new ArrayList<>();

    @Builder
    public ShortForm(String title, User user, String contents, String imgUrl, int likesCount) {
        this.user = user;
        this.title = title;
        this.contents = contents;
        this.imgUrl = imgUrl;
        this.likesCount = likesCount;
        this.createdAt = getCreatedAt();
        this.formattedCreatedAt = getFormattedCreatedAt();
    }


}