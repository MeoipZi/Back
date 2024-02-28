package MeoipZi.meoipzi.dto.ShortformDto;

import MeoipZi.meoipzi.domain.Community;
import MeoipZi.meoipzi.domain.Shortform;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class ShortformRequestDTO {
    private String username;

    private MultipartFile imgUrl;
    private String title;
    private String contents;

    public Shortform toEntity() {
        return Shortform.builder()
                .imgUrl(imgUrl.getOriginalFilename())
                .title(title)
                .contents(contents)
                .build();
    }
}
