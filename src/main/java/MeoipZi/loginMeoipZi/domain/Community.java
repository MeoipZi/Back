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
@Table(name = "COMM")
public class Community {

    @Id
    @Column(name="community_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    private String title;
    private String imgUrl;
    private String content;
    private String category;//자유게시판, 쇼핑&브랜드 중 어디인지
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int likesCount;
    private int cmtCount;

    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL)
    private List<Heart> commLikes = new ArrayList<>();

    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL)
    private List<CommentCommunity> commentCommunities = new ArrayList<>();
}