package MeoipZi.loginMeoipZi.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyFeedsDto {

    private List<MyCommResponseDto> uploadedComms;
    private List<MyImageResponseDto> uploadedSFs;
}
