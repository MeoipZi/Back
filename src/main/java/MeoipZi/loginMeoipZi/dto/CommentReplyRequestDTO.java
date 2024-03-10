package MeoipZi.loginMeoipZi.dto;

import MeoipZi.loginMeoipZi.domain.CommentCommunity;
import MeoipZi.loginMeoipZi.domain.Community;
import MeoipZi.loginMeoipZi.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentReplyRequestDTO {
    private String username;
    private String content; // 댓글 내용
    private Long parentId; // 대댓글의 경우 부모 댓글의 id

    public CommentCommunity toEntity(User user, Community community) {
        return CommentCommunity.builder()
                .content(content)
                .community(community)
                .user(user)
                .build();
    }
}
