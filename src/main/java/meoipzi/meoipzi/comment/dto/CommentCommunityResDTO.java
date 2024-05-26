package meoipzi.meoipzi.comment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import meoipzi.meoipzi.comment.domain.CommentCommunity;

@Getter
@Setter
@NoArgsConstructor
public class CommentCommunityResDTO {
    //private String nickname;
    private String content;

    public CommentCommunityResDTO(CommentCommunity commentCommunity){
        this.content = commentCommunity.getContent();
    }
}
