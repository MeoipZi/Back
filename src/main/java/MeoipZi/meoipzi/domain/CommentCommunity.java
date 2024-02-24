package MeoipZi.meoipzi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "COMMENT_COMM")
@Getter @Setter
public class CommentCommunity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST) // 다대일 단방향 관계, 게시글 삭제되면 댓글도 삭제
    @JoinColumn(name = "COMM_ID")
    private Community comm_id;   // 게시글 id(FK)

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST) // 다대일 단방향 관계, user 삭제되면 댓글도 삭제
    @JoinColumn(name = "USER_ID")
    private User user;          // 유저 id(FK)

    @Column(name = "CONTENTS")
    private String contents;

    private LocalDateTime created_at;

}
