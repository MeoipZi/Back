package meoipzi.meoipzi.shortform.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import meoipzi.meoipzi.login.domain.User;
import meoipzi.meoipzi.shortform.domain.ShortForm;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ShortformRequestDTO {
    private String username;

    @JsonIgnore
    private MultipartFile imgUrl;
    private String title;
    private String contents;

    public ShortForm toEntity(User user) {
        return ShortForm.builder()
                .imgUrl(imgUrl != null ? imgUrl.getOriginalFilename() : null)
                .title(title)
                .contents(contents)
                .user(user)
                .build();
    }
}