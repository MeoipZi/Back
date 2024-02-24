package MeoipZi.loginMeoipZi.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class CommunityResponseDto {

    private Long id;
    private String title;
    private int likesCount;
    private int cmtCount;
    private LocalDateTime createAt;
    public CommunityResponseDto(Long id, String title, int likesCount, int cmtCount, LocalDateTime createAt) {
        this.id = id;
        this.title = title;
        this.likesCount = likesCount;
        this.cmtCount = cmtCount;
        this.createAt = createAt;
    }
}
