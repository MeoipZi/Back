package meoipzi.meoipzi.shortform.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import meoipzi.meoipzi.shortform.domain.ShortForm;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@RequiredArgsConstructor
public class ShortformUpdateRequestDTO {
    private String userName;
    private String title;
    private String contents;

    @JsonIgnore
    private MultipartFile imgUrl;
}