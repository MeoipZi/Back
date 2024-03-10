package meoipzi.meoipzi.mypage.dto;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class MyCommResponseDto {

    private Long id;
    private String title;
    private int likesCount;
    private int cmtCount;
    private LocalDateTime createAt;
    public MyCommResponseDto(Long id, String title, int likesCount, int cmtCount, LocalDateTime createAt) {
        this.id = id;
        this.title = title;
        this.likesCount = likesCount;
        this.cmtCount = cmtCount;
        this.createAt = createAt;
    }
}