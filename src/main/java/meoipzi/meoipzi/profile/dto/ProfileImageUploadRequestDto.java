package meoipzi.meoipzi.profile.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import meoipzi.meoipzi.profile.domain.Profile;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ProfileImageUploadRequestDto {
    private String username;
    @JsonIgnore
    private MultipartFile imgUrl; // 프로필 이미지 업로드 구현
}

