package meoipzi.meoipzi.mypage.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyHeartsDto {
    private List<MyCommResponseDto> likedComms;
    private List<MyImageResponseDto> likedOutfits;
    private List<MyImageResponseDto> likedShortForms;
}