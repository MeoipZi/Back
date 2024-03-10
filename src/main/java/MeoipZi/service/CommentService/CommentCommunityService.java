package MeoipZi.service.CommentService;

import MeoipZi.Exception.NotFoundMemberException;
import MeoipZi.domain.CommentCommunity;
import MeoipZi.domain.Community;
import MeoipZi.domain.User;
import MeoipZi.dto.CommentDto.CommentCommunityRecDTO;
import MeoipZi.dto.CommentDto.CommentReplyRecDTO;
import MeoipZi.repository.CommentRepository.CommentCommunityRepository;
import MeoipZi.repository.CommunityRepository;
import MeoipZi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentCommunityService {
    private final UserRepository userRepository;
    private final CommunityRepository communityRepository;
    private final CommentCommunityRepository commentCommunityRepository;

    @Transactional
    public ResponseEntity<?> saveComment(Long communityId, CommentCommunityRecDTO commentCommunityRecDTO) throws Exception{
        User user = userRepository.findByUsername(commentCommunityRecDTO.getUsername())
                .orElseThrow(() -> new NotFoundMemberException("Could not found username : " + commentCommunityRecDTO.getUsername()));

        Community community = communityRepository.findById(communityId)
                .orElseThrow(() -> new RuntimeException("해당 ID의 커뮤니티를 찾을 수 없습니다."));

        //String content = commentCommunityRecDTO.getContent();
        CommentCommunity commentCommunity = commentCommunityRecDTO.toEntity(user,community);
        commentCommunityRepository.save(commentCommunity);
        return ResponseEntity.ok(commentCommunityRecDTO);
    }
    @Transactional
    public ResponseEntity<?> saveReply(Long communityId, CommentReplyRecDTO commentReplyRecDTO) throws Exception{
        User user = userRepository.findByUsername(commentReplyRecDTO.getUsername())
                .orElseThrow(() -> new NotFoundMemberException("Could not found username : " + commentReplyRecDTO.getUsername()));

        Community community = communityRepository.findById(communityId)
                .orElseThrow(() -> new RuntimeException("해당 ID의 커뮤니티를 찾을 수 없습니다."));

        CommentCommunity parentComment = commentCommunityRepository.findById(commentReplyRecDTO.getParentId())
                .orElseThrow(()->new RuntimeException("해당 부모 ID를 찾을 수 없습니다.")); // 대댓글의 경우

        CommentCommunity commentCommunity = commentReplyRecDTO.toEntity(user,community);
        commentCommunity.setParentComment(parentComment);

        commentCommunityRepository.save(commentCommunity);
        return ResponseEntity.ok(commentReplyRecDTO);
    }

    @Transactional
    public void deleteComment(Long commentId, String username) throws Exception {
        CommentCommunity comment = commentCommunityRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("해당 ID의 댓글을 찾을 수 없습니다."));

        if (!comment.getUser().getUsername().equals(username)) {
            throw new RuntimeException("삭제 권한이 없습니다.");
        }

        // parentId가 null이 아닌 경우에만 삭제
        if (comment.getParentComment() == null) {
            commentCommunityRepository.delete(comment);
        } else {
            throw new RuntimeException("댓글은 부모댓글 ID가 없어야 합니다.");
        }
    }

    @Transactional
    public void deleteReply(Long commentId, String username) throws Exception {
        CommentCommunity comment = commentCommunityRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("해당 ID의 대댓글을 찾을 수 없습니다."));

        if (!comment.getUser().getUsername().equals(username)) {
            throw new RuntimeException("삭제 권한이 없습니다.");
        }

        // parentId가 null이 아닌 경우에만 삭제
        if (comment.getParentComment() != null) {
            commentCommunityRepository.delete(comment);
        } else {
            throw new RuntimeException("부모 댓글이 없는 대댓글은 삭제할 수 없습니다.");
        }
    }


}