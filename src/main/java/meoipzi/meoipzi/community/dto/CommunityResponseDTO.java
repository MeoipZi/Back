package meoipzi.meoipzi.community.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import meoipzi.meoipzi.comment.domain.CommentCommunity;
import meoipzi.meoipzi.comment.dto.CommentCommunityResDTO;
import meoipzi.meoipzi.community.domain.Community;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@RequiredArgsConstructor
/* 커뮤니티 글 하나 상세 조회 시 반환할 DTO */
public class CommunityResponseDTO {
    private Long communityId;
    private String userName; // 작성자 닉네임(익명이면 익명이라 표시)
    private String profileImg; // 작성자 프로필 이미지
    private LocalDateTime createdAt; // 글 작성 일자
    private String title; // 커뮤니티 글 제목
    private String contents; // 내용
    private String imgUrl; // 사진 첨부
    private int likesCount; // 좋아요 개수
    private int commentsCount; // 댓글 개수
    private List<CommentCommunityResDTO> comments; // 댓글 리스트를 상세 조회 dto에 실어서 반환

    public CommunityResponseDTO(Community community, List<CommentCommunity> cmtComm) {
        this.communityId = community.getId();
        this.userName = (!community.isAnonymous())? community.getUser().getProfile().getNickname() : "익명";
        this.profileImg = community.getUser().getProfile().getImgUrl(); // 프로필 이미지 랜딩하기
        this.createdAt = community.getCreatedAt();
        this.title = community.getTitle();
        this.contents = community.getContents();
        this.imgUrl = community.getImgUrl();
        this.commentsCount = community.getCommentsCount();
        this.likesCount = community.getLikesCount();
        this.comments = cmtComm.stream()
                .map(comment -> new CommentCommunityResDTO(comment))
                .collect(Collectors.toList());
    }

}