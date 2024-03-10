package MeoipZi.loginMeoipZi.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

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

    @ManyToOne
    @JoinColumn(name="parent_id")
    private CommentCommunity parentComment;

    @OneToMany(mappedBy="parentComment")
    private List<CommentCommunity> children;

    private String content; //comment로 바꾸고 싶다...
    private LocalDateTime createdAt;

    @Builder
    public CommentCommunity(String content, LocalDateTime createdAt,User user, Community community){
        this.content = content;
        this.createdAt = createdAt;
        this.user = user;
        this.community = community;
    }

}
