package meoipzi.meoipzi.community.controller;


import lombok.RequiredArgsConstructor;
import meoipzi.meoipzi.common.config.S3Config;
import meoipzi.meoipzi.community.dto.CommunityListResponseDTO;
import meoipzi.meoipzi.community.dto.CommunityRequestDTO;
import meoipzi.meoipzi.community.dto.CommunityResponseDTO;
import meoipzi.meoipzi.community.dto.CommunityUpdateRequestDTO;
import meoipzi.meoipzi.community.repository.CommunityRepository;
import meoipzi.meoipzi.community.service.CommunityService;
import meoipzi.meoipzi.outfit.dto.OutfitRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
                                                                   @RequestParam(value = "size") int size) {
        Pageable pageable =PageRequest.of(page, size);
        Page<CommunityListResponseDTO> result = communityService.getLatestCommunityList(category, pageable);
        return ResponseEntity.ok(result);
    }

    /* 커뮤니티 게시글 리스트 보기 -> 좋아요순 조회 */
    // /communities/popular?category=brand&page=0&size=20
    @GetMapping("/popular")
    public ResponseEntity<?> getPopularListPerCategory(@RequestParam(value = "category") String category,
                                                                    @RequestParam(value = "page")int page,
                                                                    @RequestParam(value = "size") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CommunityListResponseDTO> result =  communityService.getPopularCommunintyList(category, pageable);
        return ResponseEntity.ok(result);
    }

    /* 커뮤니티 게시글 하나 상세 조회 */
    // 하나의 게시글에 해당하는 댓글 정보도 같이 가져와야 함!!! -> communityResponseDto에 싣기
    @GetMapping("/{communityId}")
    public ResponseEntity<?> getOneCommunity(@PathVariable Long communityId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            CommunityResponseDTO communityResponseDTO = communityService.viewCommunity(communityId, username);

            return new ResponseEntity<>(communityResponseDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("게시글 상세 조회에 실패하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


        /* 게시글 등록 - 사용자 로그인 여부 필요 */
    @PostMapping("")
    public ResponseEntity<?> createPost(@ModelAttribute CommunityRequestDTO communityRequestDTO,
                                        @RequestParam(value = "imgUrl", required = false) List<MultipartFile> files){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        communityRequestDTO.setUsername(authentication.getName()); // 현재 로그인한 작성자의 이름으로 작성하기

        if(authentication.isAuthenticated()) {
            return communityService.saveCommunity(communityRequestDTO, files);
        }
        else {
            return new ResponseEntity<>("로그인하지 않았으므로 게시글 작성 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }
    }


    /* 게시글 수정 - 사용자 로그인 여부 필요 */
    @PatchMapping("/{communityId}")
    public ResponseEntity<?> updatePost(@PathVariable Long communityId,
                                        @ModelAttribute CommunityUpdateRequestDTO communityUpdateRequestDTO,
                                        @RequestParam(value = "imgUrl", required = false)List<MultipartFile> files) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        communityUpdateRequestDTO.setUsername(authentication.getName());

        if(authentication.isAuthenticated()){
            return communityService.updateCommunity(communityId, communityUpdateRequestDTO, files);
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

}