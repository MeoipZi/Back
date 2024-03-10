package MeoipZi.service;

import MeoipZi.Exception.NotFoundMemberException;
import MeoipZi.config.S3Config;
import MeoipZi.domain.Profile;
import MeoipZi.domain.User;
import MeoipZi.dto.ProfileDto.ProfileRegisterRequestDto;
import MeoipZi.dto.ProfileDto.ProfileUpdateRequestDto;
import MeoipZi.repository.ProfileRepository;
import MeoipZi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProfileService {

    private final UserRepository userRepository;
    private final S3Config s3Config;
    private final ProfileRepository profileRepository;

    // 초기 프로필 등록
    @Transactional
    public ResponseEntity<?> registerProfile(ProfileRegisterRequestDto profileRegisterRequestDto) {
        User user = userRepository.findByUsername(profileRegisterRequestDto.getUserName())
                .orElseThrow(() -> new NotFoundMemberException("해당 이메일에 해당하는 회원이 없습니다. : " + profileRegisterRequestDto.getUserName()));

        try {
            if (profileRegisterRequestDto.getImgUrl() != null) {
                String filePath = s3Config.upload(profileRegisterRequestDto.getImgUrl());
                Profile profile = profileRegisterRequestDto.toEntity();
                profile.setImgUrl(filePath);
                profileRepository.save(profile);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
