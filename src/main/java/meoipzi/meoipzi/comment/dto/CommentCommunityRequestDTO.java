package meoipzi.meoipzi.comment.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import meoipzi.meoipzi.comment.domain.CommentCommunity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentCommunityRequestDTO {
    private Long userId;
    private Long parentId;
    private String content;

    public CommentCommunity toEntity(){
        return CommentCommunity.builder()
                .content(content)
                .build();
    }

}
