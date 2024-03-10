package MeoipZi.loginMeoipZi.dto;

import MeoipZi.loginMeoipZi.domain.CommentOutfit;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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