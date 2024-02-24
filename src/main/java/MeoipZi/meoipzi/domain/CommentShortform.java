package MeoipZi.meoipzi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "COMMENT_SFORM")
@Getter @Setter
public class CommentShortform {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @Column(name = "SFORM_ID")
    private Long sform_id;   // 게시글 id(FK)

    @Column(name = "USER_ID")
    private Long user_id;   // 작성자 id(FK)

    @Column(name = "CONTENTS")
    private String contents;

    private LocalDateTime created_at;
}
