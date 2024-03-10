package meoipzi.meoipzi.mypage.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class MyImageResponseDto {

    private Long id;
    private String imgUrl;
    private LocalDateTime createAt;
    public MyImageResponseDto(Long id, String imgUrl, LocalDateTime createAt) {
        this.id = id;
        this.imgUrl = imgUrl;
        this.createAt = createAt;
    }
}