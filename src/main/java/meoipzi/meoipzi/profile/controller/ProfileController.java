package meoipzi.meoipzi.profile.controller;

import lombok.RequiredArgsConstructor;
import meoipzi.meoipzi.login.domain.User;
import meoipzi.meoipzi.login.repository.UserRepository;
import meoipzi.meoipzi.profile.dto.ProfileImageUploadRequestDto;
import meoipzi.meoipzi.profile.dto.ProfileRegisterRequestDto;
import meoipzi.meoipzi.profile.dto.ProfileResponseDto;
import meoipzi.meoipzi.profile.dto.ProfileUpdateRequestDto;
import meoipzi.meoipzi.profile.repository.ProfileRepository;
import meoipzi.meoipzi.profile.service.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/profiles")
public class ProfileController {
    private final ProfileService profileService;
    private final UserRepository userRepository;
    // 초기 프로필 설정 시 이미지 업로드 API
    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(ProfileImageUploadRequestDto profileImageUploadRequestDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        profileImageUploadRequestDto.setUsername(authentication.getName());
        if(authentication.isAuthenticated()) {
            return profileService.uploadProfileImage(profileImageUploadRequestDto);
        }
        else {
            return new ResponseEntity<>("Permission denied", HttpStatus.FORBIDDEN);
        }
    }

    // 회원가입 직후 초기 프로필 설정
    @PostMapping("/settings")
    public ResponseEntity<?> setProfile(ProfileRegisterRequestDto profileRegisterRequestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        profileRegisterRequestDto.setUsername(authentication.getName());
        if(authentication.isAuthenticated()) {
            return profileService.registerProfile(profileRegisterRequestDto);
        }
        else {
            return new ResponseEntity<>("Permission denied", HttpStatus.FORBIDDEN);
        }
    }


    // 프로필 조회
    @GetMapping("/info")
    // 프로필 정보 조회
    public ResponseEntity<?> getProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {
            String username = authentication.getName();
            User user = userRepository.findByUsername(username).orElse(null);
            if (user != null) {
                return profileService.getProfileByUser(user);
            } else {
                return new ResponseEntity<>("사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<>("접근 권한이 없습니다.", HttpStatus.FORBIDDEN);
    }




    // 프로필 수정
    @PatchMapping("/{profileId}")
    public ResponseEntity<?> modifyProfile(@PathVariable Long profileId,
                                           ProfileUpdateRequestDto profileUpdateRequestDto) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        profileUpdateRequestDto.setUserName(authentication.getName());
        return profileService.updateProfile(profileId, profileUpdateRequestDto);
    }
}