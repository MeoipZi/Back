package meoipzi.meoipzi.comment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import meoipzi.meoipzi.comment.domain.CommentShortForm;
import org.yaml.snakeyaml.tokens.CommentToken;

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
