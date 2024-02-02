package meoipzi.meoipzi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "commentShortForms")
public class CommentShortForm {
    @GeneratedValue
    @Id
    @Column(name="comment_short_form_id")
    private Long id;
    String content;
    LocalDateTime createDateTime;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="shortForm_id")
    private ShortForm shortForm;
}
