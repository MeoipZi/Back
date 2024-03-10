package meoipzi.meoipzi.shortform.dto;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import meoipzi.meoipzi.shortform.domain.ShortForm;

@Getter
@Setter
@RequiredArgsConstructor
public class ShortformListResponseDTO {

    private String imgUrl; // 첨부 이미지 파일

    public ShortformListResponseDTO(ShortForm shortform){
        this.imgUrl = shortform.getImgUrl();
    }
}