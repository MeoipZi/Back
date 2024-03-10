package meoipzi.meoipzi.profile.controller;

import lombok.RequiredArgsConstructor;
import meoipzi.meoipzi.login.repository.UserRepository;
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

@Controller
@RequiredArgsConstructor
@RequestMapping("/profiles")
public class ProfileController {
    private final ProfileService profileService;
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    // 초기 프로필 설정
    @PostMapping("/settings")
    public ResponseEntity<?> setProfile(ProfileRegisterRequestDto profileRegisterRequestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        profileRegisterRequestDto.setUserName(authentication.getName());

        if(authentication.isAuthenticated()){
            try {
                return profileService.registerProfile(profileRegisterRequestDto);
            } catch(Exception e) {
                return new ResponseEntity<>("프로필 설정에 오류가 발생하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<>("접근 권한이 없습니다.", HttpStatus.FORBIDDEN);
    }

    // 프로필 조회
    @GetMapping("/info")
    public ResponseEntity<?> getProfile(ProfileResponseDto profileResponseDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication.isAuthenticated()) {
            try {
                return new ResponseEntity<>(profileResponseDto, HttpStatus.OK);
            } catch(Exception e) {
                return new ResponseEntity<>("프로필 조회 중 오류가 발생하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
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