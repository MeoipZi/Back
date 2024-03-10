package MeoipZi.loginMeoipZi.dto;

import MeoipZi.loginMeoipZi.domain.CommentShortForm;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentShortFormResponseDTO {
    //private String nickname;
    private String content;

    public CommentShortFormResponseDTO(CommentShortForm commentShortForm){
        this.content = commentShortForm.getContent();
    }
}
