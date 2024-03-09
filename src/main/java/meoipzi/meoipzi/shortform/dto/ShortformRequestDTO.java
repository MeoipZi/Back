package meoipzi.meoipzi.shortform.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import meoipzi.meoipzi.shortform.domain.ShortForm;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class ShortformRequestDTO {
    private String username;

    private MultipartFile imgUrl;
    private String title;
    private String contents;

    public ShortForm toEntity() {
        return ShortForm.builder()
                .imgUrl(imgUrl.getOriginalFilename())
                .title(title)
                .contents(contents)
                .build();
    }
}