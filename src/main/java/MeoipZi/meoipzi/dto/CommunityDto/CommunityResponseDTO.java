package MeoipZi.meoipzi.dto.CommunityDto;

import MeoipZi.meoipzi.domain.Community;
import MeoipZi.meoipzi.domain.User;
import lombok.Getter;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
/* 커뮤니티 글 하나 상세 조회 시 반환할 DTO */
public class CommunityResponseDTO {

    private Long communityId;
    private String userName; // 작성자
    private LocalDateTime createdAt; // 글 작성 일자
    private String title; // 제목
    private String contents; // 내용
    private String imgUrl; // 사진 첨부
    private Long likes_count;
    private Long cmt_count;
    private String formattedCreatedAt;
    // private String profileImg; // 작성자 프로필 사진, Profile 관련 추가 필요

    public CommunityResponseDTO(Community community) {
        this.communityId = community.getId();
        this.userName = community.getUser().getNickname();
        this.createdAt = community.getCreatedAt();
        this.title = community.getTitle();
        this.contents = community.getContents();
        this.imgUrl = community.getImgUrl();
        this.formattedCreatedAt = community.getFormattedCreatedAt();
    }

}
