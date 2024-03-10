package MeoipZi.dto.ShortformDto;


import MeoipZi.domain.ShortForm;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
/* 숏폼 하나 상세 조회 시 반환할 DTO */
public class ShortformResponseDTO {
    private Long shorformId;
    private String imgUrl;
    private String title;
    private String contents;
    private int likesCount;

    public ShortformResponseDTO(ShortForm shortform){
        this.shorformId = shortform.getId();
        this.imgUrl = shortform.getImgUrl();
        this.title = shortform.getTitle();
        this.contents = shortform.getContents();
    }
}
