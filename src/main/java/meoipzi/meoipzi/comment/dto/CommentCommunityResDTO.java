package meoipzi.meoipzi.comment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import meoipzi.meoipzi.comment.domain.CommentCommunity;
import meoipzi.meoipzi.community.domain.Community;
import meoipzi.meoipzi.login.domain.User;
import meoipzi.meoipzi.profile.domain.Profile;

@Getter
@Setter
@NoArgsConstructor
public class CommentCommunityResDTO {
    private String username;
    private String content;

    public CommentCommunityResDTO(CommentCommunity commentCommunity){
        //this.username = commentCommunity.getUsername();
        //this.username = commentCommunity.getUser().getProfile().getNickname();
        //this.username = (!community.isAnonymous())? community.getUser().getProfile().getNickname() : "익명";
        User user = commentCommunity.getUser();
        if (user == null) {
            this.username = "익명";
        } else {
            Profile profile = user.getProfile();
            if (profile == null || profile.getNickname() == null || profile.getNickname().isEmpty()) {
                this.username = "익명";
            } else {
                this.username = profile.getNickname();
            }
        }

        this.content = commentCommunity.getContent();
    }
}
