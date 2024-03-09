package meoipzi.meoipzi.shortform.dto;

import lombok.Getter;
import lombok.Setter;
import meoipzi.meoipzi.shortform.domain.ShortForm;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ShortformUpdateRequestDTO {
    private String title;
    private String contents;
    private MultipartFile imgUrl;

    public ShortForm toEntity() {
        return ShortForm.builder()
                .title(title)
                .contents(contents)
                .imgUrl(imgUrl != null ? imgUrl.getOriginalFilename() : null)
                .build();
    }
}