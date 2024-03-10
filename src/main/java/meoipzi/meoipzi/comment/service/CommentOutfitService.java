package meoipzi.meoipzi.comment.service;

import lombok.RequiredArgsConstructor;
import meoipzi.meoipzi.comment.domain.CommentOutfit;
import meoipzi.meoipzi.comment.dto.CommentOutfitRequestDTO;
import meoipzi.meoipzi.comment.dto.CommentOutfitResponseDTO;
import meoipzi.meoipzi.comment.repository.CommentOutfitRepository;
import meoipzi.meoipzi.common.excepiton.NotFoundMemberException;
import meoipzi.meoipzi.login.domain.User;
import meoipzi.meoipzi.login.repository.UserRepository;
import meoipzi.meoipzi.profile.outfit.domain.Outfit;
import meoipzi.meoipzi.profile.outfit.repository.OutfitRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentOutfitService {
    private final UserRepository userRepository;

    private final OutfitRepository outfitRepository;
    private final CommentOutfitRepository commentOutfitRepository;

    @Transactional
    public ResponseEntity<?> saveComment(Long outfitId, CommentOutfitRequestDTO commentOutfitRequestDTO) throws Exception{
        User user = userRepository.findByUsername(commentOutfitRequestDTO.getUsername())
        .orElseThrow(() -> new NotFoundMemberException("Could not found username : " + commentOutfitRequestDTO.getUsername()));

        Outfit outfit = outfitRepository.findById(outfitId)
                .orElseThrow(() -> new RuntimeException("해당 ID의 코디를 찾을 수 없습니다."));
        // 코디 댓글 수 증가
        outfit.setCommentCounts(outfit.getCommentCounts()+1);
        outfitRepository.save(outfit);
        //코디 댓글 생성
        CommentOutfit commentOutfit = commentOutfitRequestDTO.toEntity(user,outfit);
        commentOutfit.setCreatedAt(commentOutfitRequestDTO.getCreatedAt());
        commentOutfitRepository.save(commentOutfit);

        return ResponseEntity.ok(commentOutfitRequestDTO);
    }
    @Transactional
    public ResponseEntity<?> deleteComment(Long commentId, String currentUsername) throws Exception {
        CommentOutfit commentOutfit = commentOutfitRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("해당 코디 댓글 ID를 찾을 수 없습니다."));

        if (!commentOutfit.getUser().getUsername().equals(currentUsername)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("해당 댓글을 삭제할 권한이 없습니다.");
        }

        // 코디 댓글 수 감소
        Outfit outfit = commentOutfit.getOutfit();
        outfit.setCommentCounts(outfit.getCommentCounts() - 1);
        outfitRepository.save(outfit);


        commentOutfitRepository.delete(commentOutfit);

        return ResponseEntity.ok("코디 댓글이 성공적으로 삭제되었습니다.");
    }

    @Transactional
    public ResponseEntity<?> updateComment(Long commentId, CommentOutfitRequestDTO updateCommentDTO) throws Exception {
        CommentOutfit commentOutfit = commentOutfitRepository.findById(commentId)
                .orElseThrow(()-> new RuntimeException(("해당 코디 댓글 ID를 찾을 수 없습니다.")));
        //수정하려는 대상이 다른 경우
        if(!commentOutfit.getUser().getUsername().equals(updateCommentDTO.getUsername())){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("해당 댓글을 수정할 권한이 없습니다.");
        }
        commentOutfit.setContent(updateCommentDTO.getContent());
        commentOutfitRepository.save(commentOutfit);

        return ResponseEntity.ok(updateCommentDTO);
    }


    //조회될때 아이디(닉네임)이랑 글 내용 모두 조회
    public List<CommentOutfitResponseDTO> getCommentsByOutfitId(Long outfitId){
        List<CommentOutfit> commentOutfits = commentOutfitRepository.findByOutfitId(outfitId);
        return commentOutfits.stream()
                .map(CommentOutfitResponseDTO::new)
                .collect(Collectors.toList());

    }



}
