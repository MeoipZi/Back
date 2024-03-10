package meoipzi.meoipzi.community.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import meoipzi.meoipzi.community.domain.Community;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Data
@RequiredArgsConstructor
/* 커뮤니티 게시글 수정 DTO */
public class CommunityUpdateRequestDTO {
    private String userName;

    private String title; // 제목
    private String contents; // 내용
    @JsonIgnore
    private MultipartFile imgUrl; // 사진 첨부
    private String category; // 카테고리
}