package MeoipZi.meoipzi.controller;

import MeoipZi.meoipzi.config.S3Config;
import MeoipZi.meoipzi.domain.Community;
import MeoipZi.meoipzi.dto.CommunityDto.CommunityListResponseDTO;
import MeoipZi.meoipzi.dto.CommunityDto.CommunityRequestDTO;
import MeoipZi.meoipzi.dto.CommunityDto.CommunityResponseDTO;
import MeoipZi.meoipzi.repository.CommunityRepository;
import MeoipZi.meoipzi.service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/communities")
public class CommunityController {


    private CommunityRepository communityRepository;
    private final CommunityService communityService;
    private final S3Config s3Config;

    /* 커뮤니티 게시글 리스트 보기 */
    @GetMapping("")
    public ResponseEntity<?> getCommunityList(){
        try {
            List<CommunityListResponseDTO> communityListResponseDTOS = communityService.getCommunityList();

            return new ResponseEntity<>(communityListResponseDTOS, HttpStatus.OK);
            // 조회된 커뮤니티 글 리스트 형식 반환
        } catch(Exception e) {
            return new ResponseEntity<>("커뮤니티 게시글 목록 조회에 실패하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /* 커뮤니티 게시글 하나 상세 조회 */
    @GetMapping("/{communityId}")
    public ResponseEntity<?> getOneCommunity(@PathVariable Long communityId) {
        try {
            CommunityResponseDTO communityResponseDTO = communityService.viewCommunity(communityId);

            return new ResponseEntity<>(communityResponseDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("게시글 상세 조회에 실패하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /* 게시글 등록 - 사용자 로그인 여부 필요 */
    @PostMapping("")
    public ResponseEntity<?> createPost(CommunityRequestDTO communityRequestDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        communityRequestDTO.setUserName(authentication.getName()); // 현재 로그인한 작성자의 이름으로 작성하기

        if(authentication.isAuthenticated()) {
            return communityService.saveCommunity(communityRequestDTO);
        }
        else {
            return new ResponseEntity<>("로그인하지 않았으므로 게시글 작성 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }
    }

/*
    */
/* 게시글 수정 - 사용자 로그인 여부 필요 *//*

    @PatchMapping("/{communityId}")
    public ResponseEntity<String> updatePost(@PathVariable Long communityId, CommunityUpdateRequestDTO communityUpdateRequestDTO) {
        try {
            Authentication authentication = SecurityContextHolder.createEmptyContext().getAuthentication();

            Optional<Community> op = communityRepository.findById(communityId);
            if(op.isPresent()) {
                Community originalCommunity = op.get();

                if(communityUpdateRequestDTO.getImgUrl() != null) {
                    String filePath = s3Config.upload(communityUpdateRequestDTO.getImgUrl());
                    originalCommunity.setImgUrl(filePath);
                }

                originalCommunity.setCategory(communityUpdateRequestDTO.getCategory());
                originalCommunity.setTitle(communityUpdateRequestDTO.getTitle());
                originalCommunity.setContents(communityUpdateRequestDTO.getContents());
            }
            return ResponseEntity.ok("게시글을 수정했습니다.");
        } catch (IOException e) {
            return ResponseEntity.ofNullable("게시글 수정에 실패하였습니다.");
        }
    }
*/

    /* 게시글 삭제 */
    @DeleteMapping("/{communityId}")
    public ResponseEntity<?> deletePost(@PathVariable Long communityId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.isAuthenticated()) {
            try {
                communityService.deleteCommunity(communityId);
                return new ResponseEntity<>("게시글 삭제에 성공하였습니다.", HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>("게시글 삭제에 실패하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR);}
        }
        else return new ResponseEntity<>("게시글 삭제 권한이 없습니다.", HttpStatus.FORBIDDEN);
    }

}
