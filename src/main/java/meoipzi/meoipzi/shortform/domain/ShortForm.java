package meoipzi.meoipzi.shortform.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import meoipzi.meoipzi.common.BaseEntity;
import meoipzi.meoipzi.login.domain.User;

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
    private Long likes_count;

    @Column(name = "IMAGE_URL")
    @NotEmpty @NotNull
    private String imgUrl;


    private int commentCounts; //이거 추가





    @Builder
    public ShortForm(String title, User user, String contents, String imgUrl) {
        this.user = user;
        this.title = title;
        this.contents = contents;
        this.imgUrl = imgUrl;
        this.createdAt = getCreatedAt();
        this.formattedCreatedAt = getFormattedCreatedAt();
    }

    public void addLike() {
        this.likes_count++;
    }

    public void removeLike() {
        if (this.likes_count > 0) {
            this.likes_count--;
        }
    }

    // 엔티티 수정 메서드
    public void update(String title, String contents, String imgUrl) {
        this.title = title;
        this.contents = contents;
        this.imgUrl = imgUrl;
    }

}