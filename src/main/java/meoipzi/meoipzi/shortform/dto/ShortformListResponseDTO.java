package meoipzi.meoipzi.shortform.dto;


import lombok.Getter;
import meoipzi.meoipzi.shortform.domain.ShortForm;

@Getter
public class ShortformListResponseDTO {

    private String imgUrl; // 첨부 이미지 파일

    public ShortformListResponseDTO(ShortForm shortform){
        this.imgUrl = shortform.getImgUrl();
    }
}