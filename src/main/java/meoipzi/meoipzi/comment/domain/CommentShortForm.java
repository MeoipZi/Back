package meoipzi.meoipzi.comment.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import meoipzi.meoipzi.common.BaseTimeEntity;
import meoipzi.meoipzi.login.domain.User;
import meoipzi.meoipzi.shortform.domain.ShortForm;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "CMTSHORTFORM")
@NoArgsConstructor
public class CommentShortForm extends BaseTimeEntity {
    @GeneratedValue
    @Id
    @Column(name="comment_short_form_id")
    private Long id;
    String content;


    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="shortform_id")
    private ShortForm shortForm;

    @Builder
    public CommentShortForm(String content, User user, ShortForm shortForm){
        this.content = content;
        this.user = user;
        this.shortForm = shortForm;
    }
}
