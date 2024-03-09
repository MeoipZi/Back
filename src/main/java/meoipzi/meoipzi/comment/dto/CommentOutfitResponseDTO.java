package meoipzi.meoipzi.comment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import meoipzi.meoipzi.comment.domain.CommentOutfit;

@Getter
@Setter
@NoArgsConstructor
public class CommentOutfitResponseDTO {
    //private String nickname;
    private String content;

    public CommentOutfitResponseDTO(CommentOutfit commentOutfit){
        this.content = commentOutfit.getContent();
    }
}
