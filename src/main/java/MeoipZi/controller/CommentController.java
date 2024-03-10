package MeoipZi.controller;

import MeoipZi.dto.CommentDto.CommentCommunityRecDTO;
import MeoipZi.dto.CommentDto.CommentReplyRecDTO;
import MeoipZi.dto.CommentDto.CommentShortFormResponseDTO;
import MeoipZi.dto.CommentDto.CommentShortformRequestDTO;
import MeoipZi.service.CommentService.CommentCommunityService;
import MeoipZi.service.CommentService.CommentShortformService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentCommunityService commentCommunityService;
    private final CommentShortformService commentShortFormService;

    //숏폼 댓글 생성 - 로그인된 사용자가 접근
    @PostMapping("/cmtshortforms/{shortformId}")
    public ResponseEntity<?> saveCommentShortForm(@PathVariable("shortformId") Long shortformId, CommentShortformRequestDTO commentShortFormRequestDTO) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        commentShortFormRequestDTO.setUsername(authentication.getName());
        return  commentShortFormService.saveComment(shortformId, commentShortFormRequestDTO);
    }

    //숏폼 댓글 삭제 - 로그인된 사용자가 자신이 적은 글에만 접근
    @DeleteMapping("/cmtshortforms/{commentId}")
    public ResponseEntity<?> deleteCommentShortForm(@PathVariable("commentId") Long commentId) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        return commentShortFormService.deleteComment(commentId, currentUsername);
    }

    //숏폼 댓글 수정 = 로그인된 사용자가 자신이 적은 글에만 접근
    @PutMapping("/cmtshortforms/{commentId}")
    public ResponseEntity<?> updateCommentShortForm(@PathVariable("commentId")Long commentId, CommentShortformRequestDTO updateCommentDTO) throws  Exception{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        updateCommentDTO.setUsername(authentication.getName());
        return commentShortFormService.updateComment(commentId,updateCommentDTO);
    }

    //숏폼 댓글 조회 - 닉네임(이메일)이랑 댓글내용 나오게  [로그인 여부와 관계 없음]
    @GetMapping("/cmtshortforms/{shortformId}")
    public ResponseEntity<?> getCommentsByShortformId(@PathVariable("shortformId")Long shortformId){
        List<CommentShortFormResponseDTO> commentShortFormResponseDTOList = commentShortFormService.getCommentsByShortformId(shortformId);
        return ResponseEntity.ok(commentShortFormResponseDTOList);
    }

    //커뮤니티 댓글 + 대댓글 생성 - 로그인된 사용자가 접근
    @PostMapping("/cmtCommunity/{communityId}")
    public ResponseEntity<?> saveCommentCommunity(@PathVariable("communityId") Long communityId, CommentCommunityRecDTO commentCommunityRecDTO) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        commentCommunityRecDTO.setUsername(authentication.getName());
        return commentCommunityService.saveComment(communityId, commentCommunityRecDTO);
    }


    //커뮤니티 대댓글 생성 -로그인된 사용자가 접근
    @PostMapping("/replyCommunity/{communityId}")
    public ResponseEntity<?> saveReplyCommunity(@PathVariable("communityId") Long communityId, CommentReplyRecDTO commentReplyRecDTO) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        commentReplyRecDTO.setUsername(authentication.getName());
        return commentCommunityService.saveReply(communityId, commentReplyRecDTO);
    }

    //커뮤니티 댓글 삭제 - 로그인된 사용자가 접근
    @DeleteMapping("/cmtCommunity/{commentId}")
    public ResponseEntity<?> deleteCommentCommunity(@PathVariable("commentId") Long commentId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try {
            commentCommunityService.deleteComment(commentId, authentication.getName());
            return ResponseEntity.ok("커뮤니티 댓글이 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 커뮤니티 대댓글 삭제 - 로그인된 사용자가 접근
    @DeleteMapping("/replyCommunity/{commentId}")
    public ResponseEntity<?> deleteReplyCommunity(@PathVariable("commentId") Long commentId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try {
            commentCommunityService.deleteReply(commentId, authentication.getName());
            return ResponseEntity.ok("커뮤니티 대댓글이 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //커뮤니티 댓글 + 대댓글 조회 + 추가적인 커뮤니티 정보들
}