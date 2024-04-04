package meoipzi.meoipzi.outfit.controller;

import lombok.RequiredArgsConstructor;
import meoipzi.meoipzi.genre.repository.GenreRepository;
import meoipzi.meoipzi.login.repository.UserRepository;
import meoipzi.meoipzi.login.service.UserService;
import meoipzi.meoipzi.outfit.dto.*;
import meoipzi.meoipzi.outfit.service.OutfitService;
import meoipzi.meoipzi.product.dto.ProductTotalResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequiredArgsConstructor
public class OutfitController {
    private final OutfitService outfitService;
    private final UserService userService;
    private final UserRepository userRepository;


    //코디 등록하기! - 로그인여부 확인
    @PostMapping("/outfits")
    public ResponseEntity<?> saveOutfit(OutfitRequestDTO outfitRequestDTO) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        outfitRequestDTO.setUsername(authentication.getName());
        if (authentication.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"))) {
            // 사용자에게 ROLE_ADMIN이 할당되어 있을 경우
            return outfitService.saveOutfit(outfitRequestDTO);
        } else{
            // 사용자에게 ROLE_ADMIN이 할당되어 있지 않은 경우
            return new ResponseEntity<>("Permission denied", HttpStatus.FORBIDDEN);
        }

    }
    //코디 수정하기 필요한가? -> 등록할때 필요! - 로그인여부 확인
    @PatchMapping("/outfits/{outfitId}")
    public ResponseEntity<?> updateOutfit(@PathVariable("outfitId")Long outfitId, OutfitUpdateRequestDTO outfitUpdateRequestDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        outfitUpdateRequestDTO.setUsername(authentication.getName());
        return outfitService.updateOutfit(outfitId, outfitUpdateRequestDTO);
    }
    //코디 삭제하기 필요한가? -> product랑 연결땜에 안됨

    // [사진 클릭 부분] 1개 코디와 3개의 상품 조회 - 로그인여부랑 상관없음
    @GetMapping("/outfits/{outfitId}")
    public ResponseEntity<?> clickOutfit(@PathVariable("outfitId") Long outfitId){
        try{
            OutfitResponseDTO outfitResponseDTO = outfitService.clickOutfit(outfitId);
            return new ResponseEntity<>(outfitResponseDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to retrieve outfit", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    // [전체 조회 2*10 123-- 페이지네이션] 기본적으로: -[좋아요순==인기순 조회] 좋아요 개수가 같으면 최신순으로 초회됨
    @GetMapping("/outfits/popular")
    public ResponseEntity<?> getPopularOutfits(@RequestParam(value = "page")int page, @RequestParam(value = "size") int size){
        Pageable pageable = PageRequest.of(page,size);
        Page<OutfitTotalResponseDTO> popularOutfitsPage = outfitService.getPopularOutfits(pageable);

        List<List<Object>> outfitsGrid = partitionIntoRows2(
                popularOutfitsPage.getContent().stream()
                        .flatMap(outfit -> Stream.of(outfit.getOutfitId(), outfit.getImgUrl()))
                        .collect(Collectors.toList()),
                4
        );
        return ResponseEntity.ok(outfitsGrid);
    }



    //[최신순 조회]
    //http://localhost:8080/outfits/latest?page=0&size=20
    @GetMapping("/outfits/latest")
    public ResponseEntity<?> getLatestOutfits(@RequestParam(value = "page") int page, @RequestParam(value = "size") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<OutfitTotalResponseDTO> latestOutfitsPage = outfitService.getLatestOutfits(pageable);


        List<List<Object>> outfitsGrid = partitionIntoRows2(
                latestOutfitsPage.getContent().stream()
                        .flatMap(outfit -> Stream.of(outfit.getOutfitId(), outfit.getImgUrl()))
                        .collect(Collectors.toList()),
                4
        );
        return ResponseEntity.ok(outfitsGrid);
    }


    //한 장르에 어떤 코디들이 있는지 조회하는 화면 [최신순 조회]
    @GetMapping("/outfits/search/genre/latest")
    public ResponseEntity<?> getLatestOutfitsByGenre(@RequestParam(value = "genreId") Long genreId, @RequestParam(value = "page") int page, @RequestParam(value = "size") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<OutfitsGenreResponseDTO> latestOutfitsPage = outfitService.getLatestOutfitsByGenre(genreId, pageable);

        List<List<Object>> outfitsGrid = partitionIntoRows2(
                latestOutfitsPage.getContent().stream()
                        .flatMap(outfit -> Stream.of(outfit.getOutfitId(), outfit.getImgUrl()))
                        .collect(Collectors.toList()),
                4
        );

        return ResponseEntity.ok(outfitsGrid);
    }

    private List<List<Object>> partitionIntoRows2(List<Object> list, int elementsPerRow) {
        List<List<Object>> rows = new ArrayList<>();
        for (int i = 0; i < list.size(); i += elementsPerRow) {
            int end = Math.min(i + elementsPerRow, list.size());
            rows.add(list.subList(i, end));
        }
        return rows;
    }

}