package MeoipZi.meoipzi.controller;

import MeoipZi.meoipzi.config.S3Config;
import MeoipZi.meoipzi.domain.Community;
import MeoipZi.meoipzi.domain.Shortform;
import MeoipZi.meoipzi.dto.CommunityDto.CommunityListResponseDTO;
import MeoipZi.meoipzi.dto.CommunityDto.CommunityRequestDTO;
import MeoipZi.meoipzi.dto.CommunityDto.CommunityResponseDTO;
import MeoipZi.meoipzi.dto.CommunityDto.CommunityUpdateRequestDTO;
import MeoipZi.meoipzi.dto.ShortformDto.ShortformListResponseDTO;
import MeoipZi.meoipzi.dto.ShortformDto.ShortformRequestDTO;
import MeoipZi.meoipzi.dto.ShortformDto.ShortformResponseDTO;
import MeoipZi.meoipzi.dto.ShortformDto.ShortformUpdateRequestDTO;
import MeoipZi.meoipzi.repository.CommunityRepository;
import MeoipZi.meoipzi.repository.ShortformRepository;
import MeoipZi.meoipzi.service.CommunityService;
import MeoipZi.meoipzi.service.ShortformService;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/shortforms")
public class ShortformController {
    private ShortformRepository shortformRepository;
    private final ShortformService shortformService;
    private final S3Config s3Config;

    /* 숏폼 게시글 리스트 보기 */
    @GetMapping("")
    public ResponseEntity<List<ShortformListResponseDTO>> getCommunityList(){
        try {
            List<ShortformListResponseDTO> shortformListResponseDTOS = shortformService.getShortformList();

            return ResponseEntity.ok(shortformListResponseDTOS); // 조회된 커뮤니티 글 리스트 형식 반환
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).build();
        }
    }

    /* 커뮤니티 게시글 하나 상세 조회 */
    @GetMapping("/{shortformId}")
    public ResponseEntity<ShortformResponseDTO> getOneCommunity(@PathVariable Long shortformId) {
        try {
            ShortformResponseDTO shortformResponseDTO = shortformService.viewShortform(shortformId);

            return ResponseEntity.ok(shortformResponseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).build();
        }
    }

    /* 숏폼 글 등록 */
    @PostMapping("")
    public void createPost(ShortformRequestDTO shortformRequestDTO){
        shortformService.join(shortformRequestDTO);
    }

    /* 숏폼 글 삭제 */
    @DeleteMapping("/{shortformId}")
    public ResponseEntity<String> deletePost(@PathVariable Long shortformId) {
        try {
            shortformService.deleteShortform(shortformId);
            return ResponseEntity.ok("숏폼 삭제에 성공하였습니다.");
        } catch (Exception e) {
            return ResponseEntity.ofNullable("숏폼 삭제 중에 오류가 발생하였습니다.");
        }
    }

    /* 숏폼 글 수정 */
    @PatchMapping("/{shortformId}")
    /* 게시글 수정 */
    public ResponseEntity<String> updatePost(@PathVariable Long shortformId, ShortformUpdateRequestDTO shortformUpdateRequestDTO) {
        try {
           shortformService.updateShortform(shortformId, shortformUpdateRequestDTO);
            return ResponseEntity.ok("게시글을 수정했습니다.");
        } catch (Exception e) {
            return ResponseEntity.ofNullable("게시글 수정에 실패하였습니다.");
        }
    }

}

