package MeoipZi.dto.CommunityDto;

import MeoipZi.domain.Category;
import MeoipZi.domain.Community;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

/* 커뮤니티 글 등록 시 사용할 DTO */
@Getter
@Setter
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class CommunityRequestDTO {

    private String userName;    // 작성자

    private boolean isAnonymous; // 익명 여부
    @JsonIgnore
    private MultipartFile imgUrl; // 첨부 이미지 파일
    private String title; // 제목
    private String contents; // 내용
    private Category category; // 브랜드/업체 , 쇼핑&패션, 자유게시판
    public Community toEntity() {
        return Community.builder()
                .imgUrl(imgUrl != null ? imgUrl.getOriginalFilename() : null)
                .isAnonymous(isAnonymous)
                .title(title)
                .contents(contents)
                .category(category.name()) // brand, shop, play 중에 하나
                .build();
    }
}
