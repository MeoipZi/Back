package meoipzi.meoipzi.shortform.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import meoipzi.meoipzi.shortform.domain.ShortForm;

@Getter
@Setter
@NoArgsConstructor
public class ShortformListResponseDTO {

    private String imgUrl; // 첨부 이미지 파일
    private Long shortFormId;

    public ShortformListResponseDTO(ShortForm shortform){
        this.imgUrl = shortform.getImgUrl();
        this.shortFormId = shortform.getId();
    }
}