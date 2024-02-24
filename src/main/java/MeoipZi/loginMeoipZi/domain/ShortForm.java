package MeoipZi.loginMeoipZi.domain;

import MeoipZi.loginMeoipZi.domain.Heart;
import MeoipZi.loginMeoipZi.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "SHORTFORM")
@NoArgsConstructor
public class ShortForm {

    @Id
    @Column(name="shortform_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    private String title;
    private String imgUrl;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int likesCount;

    @OneToMany(mappedBy = "shortForm", cascade = CascadeType.ALL)
    private List<Heart> shortformLikes = new ArrayList<>();

    @Builder
    public ShortForm(User user, String title, String imgUrl, String content, LocalDateTime createdAt, LocalDateTime updatedAt, int likesCount){
        this.user = user;
        this.title = title;
        this.imgUrl = imgUrl;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.likesCount = likesCount;
    }
}
