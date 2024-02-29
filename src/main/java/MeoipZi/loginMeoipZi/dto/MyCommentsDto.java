package MeoipZi.loginMeoipZi.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyCommentsDto {
    private List<MyImageResponseDto> cmtOutfits;
    private List<MyImageResponseDto> cmtShortforms;
    private List<MyCommResponseDto> cmtComms;
    private List<MyCommResponseDto> cmtReplys;
}
