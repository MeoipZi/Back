package MeoipZi.loginMeoipZi.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MultipleDto {

    private List<CommunityResponseDto> uploadedComms;
    private List<ShortFormResponseDto> uploadedSFs;
}
