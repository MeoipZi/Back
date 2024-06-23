package meoipzi.meoipzi.profile.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import meoipzi.meoipzi.profile.domain.Profile;

@Getter
@Setter
@RequiredArgsConstructor
// 프로필 편집 버튼 클릭 시 들어갈 페이지
public class ProfileResponseDto {
    private Long profileId;
    private String imgUrl;
    @JsonIgnore
    private String username;
    private String nickname;
    private Integer height;
    private Integer weight;
    private boolean heightSecret;
    private boolean weightSecret;


    public ProfileResponseDto(Profile profile) {
        this.profileId = profile.getProfileId();
        this.imgUrl = profile.getImgUrl();
        this.username = profile.getCurrentUsername();
        this.nickname = profile.getNickname();
        this.height = profile.getHeight();
        this.heightSecret = profile.isHeightSecret();
        this.weight = profile.getWeight();
        this.weightSecret = profile.isWeightSecret();
    }

}