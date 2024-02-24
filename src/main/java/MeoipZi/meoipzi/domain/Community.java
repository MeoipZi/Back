package MeoipZi.meoipzi.domain;

import MeoipZi.meoipzi.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "COMMUNITY")
@Getter @Setter
@NoArgsConstructor
public class Community extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_ID", updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST) // 다대일 단방향 관계, user 삭제되면 일기도 삭제
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "LIKES_COUNT")
    private Long likes_count;

    @Column(name = "CMT_COUNT")
    private Long cmt_count;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CONTENTS")
    private String contents;

    @Column(name = "IMAGE_URL")
    private String imgUrl;

    @Column(name = "CATEGORY")
    private String category;

    @Builder
    public Community(String imgUrl, User user, String title, String contents, String category) {
        this.user = user;
        this.imgUrl = imgUrl;
        this.title = title;
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


    public void addLike() {
        this.likes_count++;
    }

    public void removeLike() {
        if (this.likes_count > 0) {
            this.likes_count--;
        }
    }
}
