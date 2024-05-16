package meoipzi.meoipzi.community.dto;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import meoipzi.meoipzi.community.domain.Category;
import meoipzi.meoipzi.community.domain.Community;
import meoipzi.meoipzi.login.domain.User;
import org.springframework.web.multipart.MultipartFile;

/* 커뮤니티 글 등록 시 사용할 DTO */
@Getter
@Setter
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class CommunityRequestDTO {
    private String username;    // 유저 Id

    private boolean isAnonymous; // 익명 여부
    @JsonIgnore
    private MultipartFile imgUrl; // 첨부 이미지 파일
    private String title; // 제목
    private String contents; // 내용
    private Category category; // 브랜드/업체 , 쇼핑&패션, 자유게시판
    public Community toEntity(User user) {
        return Community.builder()
                .imgUrl(imgUrl != null ? imgUrl.getOriginalFilename() : null)
                .isAnonymous(isAnonymous)
                .title(title)
                .contents(contents)
                .user(user)
                .category(category.name()) // brand, shop, play 중에 하나
                .build();
    }
}