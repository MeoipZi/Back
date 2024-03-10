package MeoipZi.dto.ShortformDto;

import MeoipZi.domain.ShortForm;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ShortformListResponseDTO {

    private String imgUrl; // 첨부 이미지 파일

    public ShortformListResponseDTO(ShortForm shortform){
        this.imgUrl = shortform.getImgUrl();
    }
}
