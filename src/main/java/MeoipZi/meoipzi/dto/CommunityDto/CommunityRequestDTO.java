package MeoipZi.meoipzi.dto.CommunityDto;

import MeoipZi.meoipzi.domain.Community;
import MeoipZi.meoipzi.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

/* 커뮤니티 글 등록 시 사용할 DTO */
@Getter
@Setter
@RequiredArgsConstructor
public class CommunityRequestDTO {

    private Long userId;    // 작성자
    private MultipartFile imgUrl;
    private String title;
    private String contents;
    private String category; // 브랜드/업체 , 쇼핑&패션, 자유게시판
    public Community toEntity() {
        return Community.builder()
                .imgUrl(imgUrl != null ? imgUrl.getOriginalFilename() : null)
                .title(title)
                .contents(contents)
                .category(category)
                .build();
    }
}
