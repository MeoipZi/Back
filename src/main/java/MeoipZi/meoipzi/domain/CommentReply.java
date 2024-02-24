package MeoipZi.meoipzi.domain;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "CMT_REPLY")
@Getter
@Setter
public class CommentReply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @Column(name = "CMTCOMM_ID")
    private Long cmtcommId; // 댓글 id(FK)

    @Column(name = "USER_ID")
    private Long userId; // 작성자 id(FK)

    private String contents;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;
}
