package meoipzi.meoipzi.shortform.controller;


import lombok.RequiredArgsConstructor;
import meoipzi.meoipzi.common.config.S3Config;
import meoipzi.meoipzi.shortform.dto.ShortformListResponseDTO;
import meoipzi.meoipzi.shortform.dto.ShortformRequestDTO;
import meoipzi.meoipzi.shortform.dto.ShortformResponseDTO;
import meoipzi.meoipzi.shortform.dto.ShortformUpdateRequestDTO;
import meoipzi.meoipzi.shortform.repository.ShortFormRepository;
import meoipzi.meoipzi.shortform.service.ShortformService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequiredArgsConstructor
@RequestMapping("/shortforms")
public class ShortformController {
    private ShortFormRepository shortformRepository;
    private final ShortformService shortformService;

    /* 숏폼 게시글 리스트 보기 */
    // -> 좋아요순 조회
    @GetMapping("/popular")
    public ResponseEntity<?> getPopularShortformList(@RequestParam(value = "page") int page,
                                                     @RequestParam(value = "size") int size){
        Pageable pageable = PageRequest.of(page,size);
        Page<ShortformListResponseDTO> popularShortformPage = shortformService.getPopularShortformList(pageable);

        List<List<Object>> shortformsGrid = partitionIntoRows(popularShortformPage.getContent().stream()
                .flatMap(shortform -> Stream.of(shortform.getShortFormId(), shortform.getImgUrl()))
                .collect(Collectors.toList()),4);
        return ResponseEntity.ok(shortformsGrid);
    }

    // 최신순 조회
    @GetMapping("/latest")
    public ResponseEntity<?> getLatestShortformList(@RequestParam(value = "page") int page,
                                                    @RequestParam(value = "size") int size){
        Pageable pageable = PageRequest.of(page,size);
        Page<ShortformListResponseDTO> popularShortformPage = shortformService.getLatestShortformList(pageable);

        List<List<Object>> shortformsGrid = partitionIntoRows(
                popularShortformPage.getContent().stream()
                        .flatMap(shortform -> Stream.of(shortform.getShortFormId(), shortform.getImgUrl()))
                        .collect(Collectors.toList()),4);
        return ResponseEntity.ok(shortformsGrid);
    }

    /* 숏폼 게시글 하나 상세 조회 */
    @GetMapping("/{shortformId}")
    public ResponseEntity<?> getOneShortform(@PathVariable Long shortformId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            ShortformResponseDTO shortformResponseDTO = shortformService.viewShortform(shortformId, username);

            return new ResponseEntity<>(shortformResponseDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("알 수 없는 오류가 발생하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /* 숏폼 글 등록 */
    @PostMapping("")
    public ResponseEntity<?> createShortform(ShortformRequestDTO shortformRequestDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        shortformRequestDTO.setUsername(authentication.getName());
        if(authentication.isAuthenticated()) {
            try {
                return shortformService.saveShortform(shortformRequestDTO);
                //return new ResponseEntity<>("숏폼 등록에 성공하였습니다.", HttpStatus.OK);
            } catch(Exception e) {
                return new ResponseEntity<>("숏폼 등록에 실패하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        else return new ResponseEntity<>("접근 권한이 없습니다.", HttpStatus.FORBIDDEN);
    }

    /* 숏폼 글 삭제 */
    @DeleteMapping("/{shortformId}")
    public ResponseEntity<?> deleteShortform(@PathVariable Long shortformId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication.isAuthenticated()){
            try {
                shortformService.deleteShortform(shortformId);
                return new ResponseEntity<>("숏폼이 삭제에 성공하였습니다.", HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>("숏폼 삭제 중에 오류가 발생하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        else return new ResponseEntity<>("접근 권한이 없습니다.", HttpStatus.FORBIDDEN);
    }

    /* 숏폼 글 수정 */
    @PatchMapping("/{shortformId}")
    public ResponseEntity<?> updateShortform(@PathVariable Long shortformId, ShortformUpdateRequestDTO shortformUpdateRequestDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        shortformUpdateRequestDTO.setUserName(authentication.getName());

        if(authentication.isAuthenticated()){
            try {
                return shortformService.updateShortform(shortformId, shortformUpdateRequestDTO);
            } catch (Exception e) {
                return new ResponseEntity<>("게시글 수정에 실패하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        else {
            return new ResponseEntity<>("접근 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }
    }

    private List<List<Object>> partitionIntoRows(List<Object> list, int elementsPerRow) {
        List<List<Object>> rows = new ArrayList<>();
        for (int i = 0; i < list.size(); i += elementsPerRow) {
            int end = Math.min(i + elementsPerRow, list.size());
            rows.add(list.subList(i, end));
        }
        return rows;
    }
}