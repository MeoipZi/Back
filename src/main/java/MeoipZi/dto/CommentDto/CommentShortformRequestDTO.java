package MeoipZi.dto.CommentDto;

import MeoipZi.domain.CommentShortForm;
import MeoipZi.domain.ShortForm;
import MeoipZi.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentShortformRequestDTO {
    private String username;
    private String content;

    public CommentShortForm toEntity(User user, ShortForm shortForm){
        return CommentShortForm.builder()
                .content(content)
                .shortForm(shortForm)
                .user(user)
                .build();
    }
}
