package meoipzi.meoipzi.profile.service;


import lombok.RequiredArgsConstructor;
import meoipzi.meoipzi.common.config.S3Config;
import meoipzi.meoipzi.common.excepiton.NotFoundMemberException;
import meoipzi.meoipzi.login.domain.User;
import meoipzi.meoipzi.login.repository.UserRepository;
import meoipzi.meoipzi.login.service.CustomUserDetailsService;
import meoipzi.meoipzi.profile.domain.Profile;
import meoipzi.meoipzi.profile.dto.ProfileImageUploadRequestDto;
import meoipzi.meoipzi.profile.dto.ProfileRegisterRequestDto;
import meoipzi.meoipzi.profile.dto.ProfileUpdateRequestDto;
import meoipzi.meoipzi.profile.repository.ProfileRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProfileService {

    private final UserRepository userRepository;
    private final S3Config s3Config;
    private final ProfileRepository profileRepository;
    private final CustomUserDetailsService userDetailsService;
    // 프로필 이미지 업로드
    @Transactional
    public ResponseEntity<?> uploadProfileImage(ProfileImageUploadRequestDto profileImageUploadRequestDto){
        User user = userRepository.findByUsername(profileImageUploadRequestDto.getUsername())
                .orElseThrow(() -> new NotFoundMemberException("해당 이메일에 해당하는 회원이 없습니다. : " +profileImageUploadRequestDto.getUsername()));
        try {
            if(profileImageUploadRequestDto.getImgUrl() != null) {
                String filePath = s3Config.upload(profileImageUploadRequestDto.getImgUrl());
                Profile profile = profileImageUploadRequestDto.toEntity();
                profile.setImgUrl(filePath);
                profile.setUser(user);
                profileRepository.save(profile);

                return ResponseEntity.ok(filePath);
            }
        }
        catch(IOException e){
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(null);
    }

    // 초기 프로필 등록
    @Transactional
    public ResponseEntity<?> registerProfile(ProfileRegisterRequestDto profileRegisterRequestDto) {
        User user = userRepository.findByUsername(profileRegisterRequestDto.getUsername())
                .orElseThrow(() -> new NotFoundMemberException("해당 이메일에 해당하는 회원이 없습니다. : " + profileRegisterRequestDto.getUsername()));
        // findByUser은 되는데, findByUserId는 안되는 이유?
        // User -> token별로 생기는 건가??
        Profile profile = profileRepository.findByUser(user);
        profile.setImgUrl(profile.getImgUrl());
        //profileRegisterRequestDto.setImgUrl(profile.getImgUrl());
        profile.setNickname(profileRegisterRequestDto.getNickname());
        profile.setHeight(profileRegisterRequestDto.getHeight());
        profile.setWeight(profileRegisterRequestDto.getWeight());
        profile.setHeightSecret(profileRegisterRequestDto.isHeightSecret());
        profile.setWeightSecret(profileRegisterRequestDto.isWeightSecret());
        profileRepository.save(profile);

        return ResponseEntity.ok(profileRegisterRequestDto);
    }


    // 프로필 정보 업데이트
    @Transactional
    public ResponseEntity<?> updateProfile(Long profileId, ProfileUpdateRequestDto profileUpdateRequestDto) throws IOException {
        User user = userRepository.findByUsername(profileUpdateRequestDto.getUserName())
                .orElseThrow(() -> new NotFoundMemberException("해당 회원이 조회되지 않습니다" + profileUpdateRequestDto.getUserName()));

        try {
            Profile originalProfile = profileRepository.findById(profileId)
                    .orElseThrow(()-> new RuntimeException("해당 ID에 해당하는 프로필이 없습니다."));

            if (profileUpdateRequestDto.getImgUrl() != null) {
                String filePath = s3Config.upload(profileUpdateRequestDto.getImgUrl());
                originalProfile.setImgUrl(filePath);
            }
            originalProfile.setNickname(profileUpdateRequestDto.getNickname());
            originalProfile.setHeight(profileUpdateRequestDto.getHeight());
            originalProfile.setWeight(profileUpdateRequestDto.getWeight());
            originalProfile.setHeightSecret(profileUpdateRequestDto.isHeightSecret());
            originalProfile.setWeightSecret(profileUpdateRequestDto.isWeightSecret());

            profileRepository.save(originalProfile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(profileUpdateRequestDto);
    }
}