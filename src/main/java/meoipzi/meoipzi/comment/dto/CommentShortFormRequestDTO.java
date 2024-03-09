package meoipzi.meoipzi.comment.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import meoipzi.meoipzi.comment.domain.CommentShortForm;
import meoipzi.meoipzi.login.domain.User;
import meoipzi.meoipzi.shortform.domain.ShortForm;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentShortFormRequestDTO {
    private String username;
    private String content; // 댓글 내용

    public CommentShortForm toEntity(User user, ShortForm shortForm) {
        return CommentShortForm.builder()
                .content(content)
                .shortForm(shortForm)
                .user(user)
                .build();
    }
}
