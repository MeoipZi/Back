package MeoipZi.meoipzi.service;

import MeoipZi.meoipzi.config.S3Config;
import MeoipZi.meoipzi.domain.Community;
import MeoipZi.meoipzi.domain.Shortform;
import MeoipZi.meoipzi.domain.User;
import MeoipZi.meoipzi.dto.CommunityDto.CommunityListResponseDTO;
import MeoipZi.meoipzi.dto.CommunityDto.CommunityResponseDTO;
import MeoipZi.meoipzi.dto.CommunityDto.CommunityUpdateRequestDTO;
import MeoipZi.meoipzi.dto.ShortformDto.ShortformListResponseDTO;
import MeoipZi.meoipzi.dto.ShortformDto.ShortformRequestDTO;
import MeoipZi.meoipzi.dto.ShortformDto.ShortformResponseDTO;
import MeoipZi.meoipzi.dto.ShortformDto.ShortformUpdateRequestDTO;
import MeoipZi.meoipzi.dto.UserDto;
import MeoipZi.meoipzi.repository.ShortformRepository;
import MeoipZi.meoipzi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ShortformService {
    private final ShortformRepository shortformRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final S3Config s3Config;

    // 모든 커뮤니티 글 리스트 조회
    public List<ShortformListResponseDTO> getShortformList() {
        try {
            return shortformRepository.findAll()
                    .stream()
                    .map(ShortformListResponseDTO::new)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    // 숏폼 글 등록
    public void join(ShortformRequestDTO shortformRequestDTO) {
        try {
            if (shortformRequestDTO.getImgUrl() != null) {
                String filePath = s3Config.upload(shortformRequestDTO.getImgUrl());
                shortformRequestDTO.toEntity().setImgUrl(filePath);
            }
            Long userId = shortformRequestDTO.getUserId();

            Optional<User> user = userRepository.findById(userId);

            Shortform shortform = shortformRequestDTO.toEntity();
            shortform.setUser(user.get());

            shortformRepository.save(shortformRequestDTO.toEntity());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 숏폼 글 삭제
    @Transactional
    public void deleteShortform(Long communityId) {
        shortformRepository.deleteById(communityId); // 글 삭제
    }

    // 숏폼 글 수정
    @Transactional
    public void updateShortform(Long shortformId, ShortformUpdateRequestDTO shortformUpdateRequestDTO) throws IOException {

        Shortform shortform = shortformRepository.findById(shortformId)
                .orElseThrow(() -> new RuntimeException("숏폼 게시글을 찾을 수 없습니다."));
        Optional<Shortform> sf = shortformRepository.findById(shortformId);
        if (sf.isPresent()) {
            Shortform originalShortform = sf.get();

            if (shortformUpdateRequestDTO.getImgUrl() != null) {
                String filePath = s3Config.upload(shortformUpdateRequestDTO.getImgUrl());
                originalShortform.setImgUrl(filePath);
            }
            originalShortform.setTitle(shortformUpdateRequestDTO.getTitle());
            originalShortform.setContents(shortformUpdateRequestDTO.getContents());
        }
    }

    // 숏폼 하나 상세 조회
    public ShortformResponseDTO viewShortform(Long communityId){
        try {
            // 조회할 커뮤니티글이 존재하는지 확인
            Shortform shortform = shortformRepository.findById(communityId)
                    .orElseThrow(()-> new NoSuchElementException("조회할 숏폼이 존재하지 않습니다."));

            return new ShortformResponseDTO(shortform);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "조회할 숏폼이 존재하지 않습니다.");
        }
    }

}
