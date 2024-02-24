package MeoipZi.meoipzi.dto.ShortformDto;

import MeoipZi.meoipzi.domain.Shortform;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ShortformUpdateRequestDTO {
    private String title;
    private String contents;
    private MultipartFile imgUrl;

    public Shortform toEntity() {
        return Shortform.builder()
                .title(title)
                .contents(contents)
                .imgUrl(imgUrl != null ? imgUrl.getOriginalFilename() : null)
                .build();
    }
}
