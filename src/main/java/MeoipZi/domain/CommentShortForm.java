package MeoipZi.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "commentShortForms")
@NoArgsConstructor
public class CommentShortForm {
    @GeneratedValue
    @Id
    @Column(name="comment_short_form_id")
    private Long id;
    String content;
    LocalDateTime createDateTime;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="shortform_id")
    private ShortForm shortForm;

    @Builder
    public CommentShortForm(String content, LocalDateTime createDateTime, User user, ShortForm shortForm){
        this.content = content;
        this.createDateTime = createDateTime;
        this.user = user;
        this.shortForm = shortForm;
    }

}
