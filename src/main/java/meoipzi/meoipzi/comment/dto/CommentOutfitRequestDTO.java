package meoipzi.meoipzi.comment.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import meoipzi.meoipzi.comment.domain.CommentOutfit;
import meoipzi.meoipzi.login.domain.User;
import meoipzi.meoipzi.outfit.domain.Outfit;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentOutfitRequestDTO {
    private String username;
    private String content; // 댓글 내용
    //private LocalDateTime createdAt;
    // private String category; // outfit인지 숏폼인지 커뮤니티인지 구분

    public CommentOutfit toEntity(User user, Outfit outfit) {
        return CommentOutfit.builder()
                .content(content)
                .outfit(outfit)
                .user(user)
                .build();
    }
}
