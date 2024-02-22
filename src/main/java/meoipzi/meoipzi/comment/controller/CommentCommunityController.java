package meoipzi.meoipzi.comment.controller;

import lombok.RequiredArgsConstructor;
import meoipzi.meoipzi.comment.dto.CommentCommunityRequestDTO;
import meoipzi.meoipzi.comment.service.CommentCommunityService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentCommunityController {
    private final CommentCommunityService commentCommunityService;

    @PostMapping("/{communityId}")
    public void addComment(@PathVariable("communityId")Long communityId, CommentCommunityRequestDTO commentCommunityRequestDTO){
        commentCommunityService.addComment(communityId, commentCommunityRequestDTO);
    }

    @DeleteMapping("/{commentCommunityId}")
    public void delete (@PathVariable("commentCommunityId")Long commentCommunityId){
        commentCommunityService.delete(commentCommunityId);
    }
}
