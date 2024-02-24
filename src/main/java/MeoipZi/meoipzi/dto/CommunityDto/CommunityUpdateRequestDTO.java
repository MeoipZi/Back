package MeoipZi.meoipzi.dto.CommunityDto;

import MeoipZi.meoipzi.domain.Community;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Data
/* 커뮤니티 게시글 수정 DTO */
public class CommunityUpdateRequestDTO {
    private String title; // 제목
    private String contents; // 내용
    private MultipartFile imgUrl; // 사진 첨부
    private String category; // 카테고리

    public Community toEntity() {
        return Community.builder()
                .title(title)
                .contents(contents)
                .imgUrl(imgUrl != null ? imgUrl.getOriginalFilename() : null)
                .category(category)
                .build();
    }
}
