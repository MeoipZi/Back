package meoipzi.meoipzi.profile.dto;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import meoipzi.meoipzi.login.domain.User;
import meoipzi.meoipzi.profile.domain.Profile;
import org.springframework.web.multipart.MultipartFile;


@Getter
@Setter
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ProfileRegisterRequestDto {
    @JsonIgnore
    private String username;    // 유저 Id

    private String imgUrl; // 프로필 사진
    private Integer height;  // 신장
    private boolean heightSecret; // 신장 공개 여부
    private Integer weight;  // 몸무게
    private boolean weightSecret; // 몸무게 공개 여부
    private String nickname;  // 닉네임

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public void setHeightSecret(boolean heightSecret) {
        this.heightSecret = heightSecret;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public void setWeightSecret(boolean weightSecret) {
        this.weightSecret = weightSecret;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public Profile toEntity(User user) {
        return Profile.builder()
                .imgUrl(imgUrl)
                .height(height)
                .weight(weight)
                .nickname(nickname)
                .heightSecret(heightSecret)
                .weightSecret(weightSecret)
                .user(user)
                .build();
    }

}