package meoipzi.meoipzi.profile.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import meoipzi.meoipzi.profile.domain.Profile;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@RequiredArgsConstructor
public class ProfileUpdateRequestDto {
    private String username;    // 유저 Id
    @JsonIgnore
    private MultipartFile imgUrl;  // 프로필 사진
    private Integer height;  // 신장
    private boolean heightSecret; // 신장 공개 여부
    private Integer weight;  // 몸무게
    private boolean weightSecret; // 몸무게 공개 여부
    private String nickname;  // 닉네임

}