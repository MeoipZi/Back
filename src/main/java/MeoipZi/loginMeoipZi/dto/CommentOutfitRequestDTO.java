package MeoipZi.loginMeoipZi.dto;

import MeoipZi.loginMeoipZi.domain.CommentOutfit;
import MeoipZi.loginMeoipZi.domain.Outfit;
import MeoipZi.loginMeoipZi.domain.User;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentOutfitRequestDTO {
    private String username;
    private String content; // 댓글 내용

    public CommentOutfit toEntity(User user, Outfit outfit) {
        return CommentOutfit.builder()
                .content(content)
                .outfit(outfit)
                .user(user)
                .build();
    }
}