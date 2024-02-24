package MeoipZi.loginMeoipZi.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class ShortFormResponseDto {

    private Long id;
    private String title;
    private String imgUrl;
    private LocalDateTime createAt;
    public ShortFormResponseDto(Long id, String title, String imgUrl, LocalDateTime createAt) {
        this.id = id;
        this.title = title;
        this.imgUrl = imgUrl;
        this.createAt = createAt;
    }
}
