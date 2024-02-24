package MeoipZi.loginMeoipZi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "CMTCOMM")
public class CommentCommunity {

    @Id
    @Column(name="cmtcomm_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="community_id")
    private Community community;

    private String contents;
    private LocalDateTime createdAt;


}
