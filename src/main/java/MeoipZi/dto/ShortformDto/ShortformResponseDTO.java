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
    private String imgUrl; // 숏폼
    private String title; // 제목
    private String contents; // 내용
    private int likesCount; // 좋아요 개수
    // private boolean isLikedByUser; // 현재 로그인한 사용자가 좋아요 눌렀는지
    private int commentsCount; // 댓글 개수

    public ShortformResponseDTO(ShortForm shortform){
        this.shorformId = shortform.getId();
        this.imgUrl = shortform.getImgUrl();
        this.title = shortform.getTitle();
        this.contents = shortform.getContents();
        this.likesCount = shortform.getLikesCount();
        this.commentsCount = shortform.getCommentsCount();
    }
}
