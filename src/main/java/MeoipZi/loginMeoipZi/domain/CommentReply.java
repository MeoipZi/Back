package MeoipZi.loginMeoipZi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "CMTREPLY")
public class CommentReply {
    @Id
    @Column(name="cmtreply_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="cmtcomm_id")
    private CommentCommunity cmtcomm;

    private String contents;
    private LocalDateTime createdAt;
}
