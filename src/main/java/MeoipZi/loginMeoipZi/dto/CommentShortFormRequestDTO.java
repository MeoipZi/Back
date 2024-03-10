package MeoipZi.loginMeoipZi.dto;

import MeoipZi.loginMeoipZi.domain.CommentShortForm;
import MeoipZi.loginMeoipZi.domain.ShortForm;
import MeoipZi.loginMeoipZi.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
