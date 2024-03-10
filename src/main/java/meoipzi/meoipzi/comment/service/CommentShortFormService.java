package meoipzi.meoipzi.comment.service;

import lombok.RequiredArgsConstructor;
import meoipzi.meoipzi.comment.domain.CommentShortForm;
import meoipzi.meoipzi.comment.dto.CommentShortFormRequestDTO;
import meoipzi.meoipzi.comment.dto.CommentShortFormResponseDTO;
import meoipzi.meoipzi.comment.repository.CommentShortFormRepository;
import meoipzi.meoipzi.common.excepiton.NotFoundMemberException;
import meoipzi.meoipzi.login.domain.User;
import meoipzi.meoipzi.login.repository.UserRepository;
import meoipzi.meoipzi.shortform.domain.ShortForm;
import meoipzi.meoipzi.shortform.repository.ShortFormRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentShortFormService {
    private final UserRepository userRepository;
    private final ShortFormRepository shortFormRepository;
    private final CommentShortFormRepository commentShortFormRepository;

    @Transactional
    public ResponseEntity<?> saveComment(Long shortformId, CommentShortFormRequestDTO commentShortFormRequestDTO) throws Exception{
        User user = userRepository.findByUsername(commentShortFormRequestDTO.getUsername())
                .orElseThrow(() -> new NotFoundMemberException("Could not found username : " + commentShortFormRequestDTO.getUsername()));

        ShortForm shortForm = shortFormRepository.findById(shortformId)
                .orElseThrow(() -> new RuntimeException("해당 ID의 숏폼을 찾을 수 없습니다."));

        shortForm.setCommentsCount(shortForm.getCommentsCount()+1);
        shortFormRepository.save(shortForm);

        CommentShortForm commentShortForm = commentShortFormRequestDTO.toEntity(user,shortForm);
        commentShortFormRepository.save(commentShortForm);

        return ResponseEntity.ok(commentShortFormRequestDTO);
    }

    @Transactional
    public ResponseEntity<?> deleteComment(Long commentId, String currentUsername) throws Exception {
        CommentShortForm commentShortForm = commentShortFormRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("해당 숏폼 댓글 ID를 찾을 수 없습니다."));

        if (!commentShortForm.getUser().getUsername().equals(currentUsername)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("해당 댓글을 삭제할 권한이 없습니다.");
        }

        ShortForm shortForm = commentShortForm.getShortForm();
        shortForm.setCommentsCount(shortForm.getCommentsCount() -1);
        shortFormRepository.save(shortForm);

        commentShortFormRepository.delete(commentShortForm);

        return ResponseEntity.ok("숏폼 댓글이 성공적으로 삭제되었습니다.");
    }

    @Transactional
    public ResponseEntity<?> updateComment(Long commentId, CommentShortFormRequestDTO updateCommentDTO) throws Exception {
        CommentShortForm commentShortForm = commentShortFormRepository.findById(commentId)
                .orElseThrow(()-> new RuntimeException(("해당 숏폼 댓글 ID를 찾을 수 없습니다.")));
        //수정하려는 대상이 다른 경우
        if(!commentShortForm.getUser().getUsername().equals(updateCommentDTO.getUsername())){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("해당 댓글을 수정할 권한이 없습니다.");
        }
        commentShortForm.setContent(updateCommentDTO.getContent());
        commentShortFormRepository.save(commentShortForm);

        return ResponseEntity.ok(updateCommentDTO);
    }

    //조회될때 아이디(닉네임)이랑 글 내용 모두 조회
    public List<CommentShortFormResponseDTO> getCommentsByShortformId(Long shortformId){
        List<CommentShortForm> commentShortForms = commentShortFormRepository.findByShortFormId(shortformId);
        return commentShortForms.stream()
                .map(CommentShortFormResponseDTO::new)
                .collect(Collectors.toList());

    }
}