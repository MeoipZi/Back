package MeoipZi.meoipzi.dto.ShortformDto;

import MeoipZi.meoipzi.domain.Shortform;
import lombok.Getter;

@Getter
public class ShortformListResponseDTO {

    private String imgUrl; // 첨부 이미지 파일

    public ShortformListResponseDTO(Shortform shortform){
        this.imgUrl = shortform.getImgUrl();
    }
}
