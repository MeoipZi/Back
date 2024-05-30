package meoipzi.meoipzi.comment.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import meoipzi.meoipzi.comment.domain.CommentCommunity;
import meoipzi.meoipzi.community.domain.Community;
import meoipzi.meoipzi.login.domain.User;
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentReplyRecDTO {
    private String username;
    private String content; // 댓글 내용
    private Long parentId; // 대댓글의 경우 부모 댓글의 id

    public CommentCommunity toEntity(User user, Community community) {
        return CommentCommunity.builder()
                .content(content)
                .username(username)
                .community(community)
                .user(user)
                .build();
    }
}
