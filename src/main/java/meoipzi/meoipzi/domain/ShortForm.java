package meoipzi.meoipzi.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import meoipzi.meoipzi.comment.domain.CommentShortForm;
import meoipzi.meoipzi.heart.domain.Heart;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "shortForms")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShortForm {
    @GeneratedValue
    @Id
    @Column(name="short_form_id")
    private Long id;

    private String imgUrl;
    private String title;
    private String content;
    private LocalDateTime createDateTime;
    private LocalDateTime updateDatetime;

    private int likesCount; //이거 추가
    private int commentCounts; //이거 추가

    @OneToMany(mappedBy = "shortForm")
    private List<Heart> likes = new ArrayList<>();

    @OneToMany(mappedBy = "shortForm")
    private List<CommentShortForm> commentShortForms = new ArrayList<>();
}
