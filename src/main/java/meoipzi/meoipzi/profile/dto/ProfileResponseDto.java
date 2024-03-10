package meoipzi.meoipzi.profile.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
// 프로필 편집 버튼 클릭 시 들어갈 페이지
public class ProfileResponseDto {
    @JsonIgnore
    private String imgUrl;
    private String nickname;
    private Integer height;
    private Integer weight;
    private boolean heightSecret;
    private boolean weightSecret;
}