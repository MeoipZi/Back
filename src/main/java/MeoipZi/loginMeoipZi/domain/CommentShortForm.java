package MeoipZi.loginMeoipZi.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "CMTSF")
public class CommentShortForm {

    @Id
    @Column(name="cmtshortform_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="shortform_id")
    private ShortForm shortForm;

    private String content;
    private LocalDateTime createdAt;

    @Builder
    public CommentShortForm(String content, LocalDateTime createDateTime, User user, ShortForm shortForm){
        this.content = content;
        this.createdAt = createDateTime;
        this.user = user;
        this.shortForm = shortForm;
    }

}
