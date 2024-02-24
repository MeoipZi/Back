package MeoipZi.meoipzi.controller;

import MeoipZi.meoipzi.config.S3Config;
import MeoipZi.meoipzi.domain.Community;
import MeoipZi.meoipzi.dto.CommunityDto.CommunityListResponseDTO;
import MeoipZi.meoipzi.dto.CommunityDto.CommunityRequestDTO;
import MeoipZi.meoipzi.dto.CommunityDto.CommunityResponseDTO;
import MeoipZi.meoipzi.dto.CommunityDto.CommunityUpdateRequestDTO;
import MeoipZi.meoipzi.repository.CommunityRepository;
import MeoipZi.meoipzi.service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/communities")
public class CommunityController {


    private CommunityRepository communityRepository;
    private final CommunityService communityService;
    private final S3Config s3Config;

    /* 커뮤니티 게시글 리스트 보기 */
    @GetMapping("")
    public ResponseEntity<List<CommunityListResponseDTO>> getCommunityList(){
        try {
            List<CommunityListResponseDTO> communityListResponseDTOS = communityService.getCommunityList();

            return ResponseEntity.ok(communityListResponseDTOS); // 조회된 커뮤니티 글 리스트 형식 반환
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).build();
        }
    }

    /* 커뮤니티 게시글 하나 상세 조회 */
    @GetMapping("/{communityId}")
    public ResponseEntity<CommunityResponseDTO> getOneCommunity(@PathVariable Long communityId) {
        try {
            CommunityResponseDTO communityResponseDTO = communityService.viewCommunity(communityId);

            return ResponseEntity.ok(communityResponseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).build();
        }
    }

    /* 게시글 등록 */
    @PostMapping("")
    public void createPost(CommunityRequestDTO communityRequestDTO){
        communityService.join(communityRequestDTO);
    }

    /* 게시글 수정 */
    @PatchMapping("/{communityId}")
    public ResponseEntity<String> updatePost(@PathVariable Long communityId, CommunityUpdateRequestDTO communityUpdateRequestDTO) {
        try {
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

    /* 게시글 삭제 */
    @DeleteMapping("/{communityId}")
    public ResponseEntity<String> deletePost(@PathVariable Long communityId) {
        try {
            communityService.deleteCommunity(communityId);
            return ResponseEntity.ok("게시글 삭제에 성공하였습니다.");
        } catch (Exception e) {
            return ResponseEntity.ofNullable("게시글 삭제 중에 오류가 발생하였습니다.");
        }
    }

}
