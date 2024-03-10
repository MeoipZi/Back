package MeoipZi.loginMeoipZi.dto;

import MeoipZi.loginMeoipZi.domain.CommentCommunity;
import MeoipZi.loginMeoipZi.domain.Community;
import MeoipZi.loginMeoipZi.domain.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentCommunityRequestDTO {
    private String username;
    private String content; // 댓글 내용
    //private Long parentCommentId; // 대댓글의 경우 부모 댓글의 id

    public CommentCommunity toEntity(User user, Community community) {
        return CommentCommunity.builder()
                .content(content)
                .community(community)
                .user(user)
                .build();
    }
}
