package MeoipZi.controller;

import MeoipZi.config.S3Config;
import MeoipZi.dto.CommunityDto.*;
import MeoipZi.repository.CommunityRepository;
import MeoipZi.service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/communities")
public class CommunityController {
    private CommunityRepository communityRepository;
    private final CommunityService communityService;
    private final S3Config s3Config;

    /* 커뮤니티 게시글 리스트 보기 -> 최신순 조회 */
    // /communities/latest?category=brand&page=0&size=20
    @GetMapping("/latest")
    public ResponseEntity<?> getLatestListPerCategory(@RequestParam(value = "category") String category,
                                                                   @RequestParam(value = "page")int page,
                                                                   @RequestParam(value = "size") int size){
        Pageable pageable = PageRequest.of(page, size);
        // 조회된 커뮤니티 글 리스트 형식 반환
        Page<CommunityListResponseDTO> latestCommunityPage = communityService.getLatestCommunityList(category, pageable);

        List<List<String>> communityGrid = partitionIntoRows(latestCommunityPage.getContent().
                stream()
                .map(CommunityListResponseDTO::getImgUrl)
                .collect(Collectors.toList()), 2);
        return ResponseEntity.ok(communityGrid);
    }

    /* 커뮤니티 게시글 리스트 보기 -> 좋아요순 조회 */
    // /communities/popular?category=brand&page=0&size=20
    @GetMapping("/popular")
    public ResponseEntity<?> getPopularListPerCategory(@RequestParam(value = "category") String category,
                                                      @RequestParam(value = "page")int page,
                                                      @RequestParam(value = "size") int size){
        Pageable pageable = PageRequest.of(page, size);
        // 조회된 커뮤니티 글 리스트 형식 반환
        Page<CommunityListResponseDTO> latestCommunityPage = communityService.getPopularCommunintyList(category, pageable);

        List<List<String>> communityGrid = partitionIntoRows(latestCommunityPage.getContent().
                stream()
                .map(CommunityListResponseDTO::getImgUrl)
                .collect(Collectors.toList()), 2);
        return ResponseEntity.ok(communityGrid);
    }

    /* 커뮤니티 게시글 하나 상세 조회 */
    // 하나의 게시글에 해당하는 댓글 정보도 같이 가져와야 함!!! -> communityResponseDto에 싣기
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


    /* 게시글 수정 - 사용자 로그인 여부 필요 */
    @PatchMapping("/{communityId}")
    public ResponseEntity<?> updatePost(@PathVariable Long communityId, CommunityUpdateRequestDTO communityUpdateRequestDTO) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        communityUpdateRequestDTO.setUserName(authentication.getName());

        if(authentication.isAuthenticated()){
            return communityService.updateCommunity(communityId, communityUpdateRequestDTO);
        }
        else {
            return new ResponseEntity<>("커뮤니티 게시글 수정 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }
    }

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

    private List<List<String>> partitionIntoRows(List<String> list, int elementsPerRow) {
        List<List<String>> rows = new ArrayList<>();
        for (int i = 0; i < list.size(); i += elementsPerRow) {
            int end = Math.min(i + elementsPerRow, list.size());
            rows.add(list.subList(i, end));
        }
        return rows;
    }
}
