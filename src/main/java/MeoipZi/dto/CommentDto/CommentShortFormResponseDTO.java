package MeoipZi.dto.CommentDto;

import MeoipZi.domain.CommentShortForm;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class CommentShortFormResponseDTO {
    private String content;
    public CommentShortFormResponseDTO(CommentShortForm commentShortForm) {
        this.content = commentShortForm.getContent();
    }
}
