package MeoipZi.dto.ProfileDto;

import MeoipZi.domain.Profile;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;


@Getter
@Setter
@RequiredArgsConstructor
public class ProfileRegisterRequestDto {
    private String userName;    // 유저 Id

    private MultipartFile imgUrl;  // 프로필 사진
    private Integer height;  // 신장
    private boolean heightSecret; // 신장 공개 여부
    private Integer weight;  // 몸무게
    private boolean weightSecret; // 몸무게 공개 여부
    private String nickname;  // 닉네임

    public Profile toEntity() {
        return Profile.builder()
                .imgUrl(imgUrl != null ? imgUrl.getOriginalFilename() : null)
                .height(height)
                .weight(weight)
                .nickname(nickname)
                .heightSecret(heightSecret)
                .weightSecret(weightSecret)
                .build();
    }

}
