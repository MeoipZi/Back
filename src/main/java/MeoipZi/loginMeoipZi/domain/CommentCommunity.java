package MeoipZi.loginMeoipZi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    @JoinColumn(name="parent_cmt_id")
    private CommentCommunity parentCmt;

    @OneToMany(mappedBy="parentCmt")
    private List<CommentCommunity> childComments;

    private String contents; //comment로 바꾸고 싶다...
    private LocalDateTime createdAt;

}
