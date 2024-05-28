package meoipzi.meoipzi.community.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import meoipzi.meoipzi.community.domain.Community;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@Data
@RequiredArgsConstructor
/* 커뮤니티 게시글 수정 DTO */
public class CommunityUpdateRequestDTO {
    private String username;

    private String title; // 제목

    private String contents; // 내용

    @JsonIgnore
    private List<MultipartFile> imgUrl; // 첨부 이미지 파일

//    private List<Long> deleteImageIds; // 삭제할 이미지 파일들

    private String category; // 카테고리
}