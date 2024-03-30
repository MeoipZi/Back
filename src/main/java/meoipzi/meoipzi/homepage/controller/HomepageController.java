package meoipzi.meoipzi.homepage.controller;

import lombok.RequiredArgsConstructor;
import meoipzi.meoipzi.homepage.dto.*;
import meoipzi.meoipzi.homepage.service.HomePageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/meoipzi")
public class HomepageController {

    private final HomePageService homePageService;
    // 빈티지 소식 등록하기 - POST /meoipzi/news
    @PostMapping("/news")
    public ResponseEntity<?> saveVintage(VintageNewsRequestDTO vintagenewsRequestDTO) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        vintagenewsRequestDTO.setUsername(authentication.getName());

        if (authentication.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"))) {
            // 사용자에게 ROLE_ADMIN이 할당되어 있을 경우
            return homePageService.saveVintage(vintagenewsRequestDTO);
        } else {
            // 사용자에게 ROLE_ADMIN이 할당되어 있지 않은 경우
            return new ResponseEntity<>("Permission denied", HttpStatus.FORBIDDEN);
        }

    }

    // 제휴기업 정보 등록하기 -> POST /meoipzi/partners
    @PostMapping("/partners")
    public ResponseEntity<?> savePartners(PartnersRequestDTO partnersRequestDTO) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        partnersRequestDTO.setUsername(authentication.getName());

        if (authentication.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"))) {
            // 사용자에게 ROLE_ADMIN이 할당되어 있을 경우
            return homePageService.savePartners(partnersRequestDTO);
        } else {
            // 사용자에게 ROLE_ADMIN이 할당되어 있지 않은 경우
            return new ResponseEntity<>("Permission denied", HttpStatus.FORBIDDEN);
        }

    }


    //빈티지 소식 삭제하기 - 로그인 여부확인 -> DELETE /news/{vintageNewsId}
    @DeleteMapping("/news/{vintageNewsId}")
    public ResponseEntity<?> deleteVintage(@PathVariable("vintageNewsId") Long vintageNewsId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"))) {
            // 사용자에게 ROLE_ADMIN이 할당되어 있을 경우
            try {
                homePageService.deleteVintage(vintageNewsId);
                return new ResponseEntity<>("VintageNews successfully deleted", HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>("Failed to delete product", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            // 사용자에게 ROLE_ADMIN이 할당되어 있지 않은 경우
            return new ResponseEntity<>("Permission denied", HttpStatus.FORBIDDEN);
        }
    }

    // 제휴 기업 정보 삭제하기 - 로그인 여부확인 -> DELETE /meoipzi/partners/{partnersId}
    @DeleteMapping("/partners/{partnersId}")
    public ResponseEntity<?> deletePartners(@PathVariable("partnersId") Long partnersId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"))) {
            // 사용자에게 ROLE_ADMIN이 할당되어 있을 경우
            try {
                homePageService.deletePartners(partnersId);
                return new ResponseEntity<>("Partner Info successfully deleted", HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>("Failed to delete partners info", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            // 사용자에게 ROLE_ADMIN이 할당되어 있지 않은 경우
            return new ResponseEntity<>("Permission denied", HttpStatus.FORBIDDEN);
        }
    }

    // view -> 관리자 권한, 로그인 여부 없이도 조회 가능
    @GetMapping("")
    public ResponseEntity<?> getHomepageInfo(){
        try {
            HomePageDTO homepageDTO = homePageService.getHomepageInfo();
            return new ResponseEntity<>(homepageDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to retrieve homepage information", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 빈티지 소식 상세조회  - [ 1개의 빈티지 소식 클릭시 ]
    @GetMapping("/news/{vintageNewsId}")
    public ResponseEntity<?> getOneVintage(@PathVariable("vintageNewsId") Long vintageNewsId){
        try {
            VintageNewsResponseDTO vintageNewsResponseDTO = homePageService.clickVintage(vintageNewsId);
            return new ResponseEntity<>(vintageNewsResponseDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to retrieve vintage information", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 제휴 기업 링크 연결 - [ 1개의 제휴 기업 클릭 시 ]
    @GetMapping("/partners/{partnersId}")
    public ResponseEntity<?> linkToOnePartner(@PathVariable("partnersId") Long partnersId){
        try {
            PartnersDetailResponseDTO partnersDetailResponseDTO = homePageService.clickPartners(partnersId);
            return new ResponseEntity<>(partnersDetailResponseDTO, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>("Failed to retreive partner information", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}