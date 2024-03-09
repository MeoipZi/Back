package meoipzi.meoipzi.shortform.dto;

import meoipzi.meoipzi.shortform.domain.ShortForm;

/* 숏폼 하나 상세 조회 시 반환할 DTO */
public class ShortformResponseDTO {
    private Long shorformId;
    private String imgUrl;
    private String title;
    private String contents;
    private int heart_count;
    private int cmt_count;

    public ShortformResponseDTO(ShortForm shortform){
        this.shorformId = shortform.getId();
        this.imgUrl = shortform.getImgUrl();
        this.title = shortform.getTitle();
        this.contents = shortform.getContents();
    }
}