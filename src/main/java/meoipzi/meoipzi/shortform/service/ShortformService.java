package meoipzi.meoipzi.shortform.service;

import lombok.RequiredArgsConstructor;
import meoipzi.meoipzi.common.config.S3Config;
import meoipzi.meoipzi.common.excepiton.NotFoundMemberException;
import meoipzi.meoipzi.login.domain.User;
import meoipzi.meoipzi.login.repository.UserRepository;
import meoipzi.meoipzi.login.service.UserService;
import meoipzi.meoipzi.shortform.domain.ShortForm;
import meoipzi.meoipzi.shortform.dto.ShortformListResponseDTO;
import meoipzi.meoipzi.shortform.dto.ShortformRequestDTO;
import meoipzi.meoipzi.shortform.dto.ShortformResponseDTO;
import meoipzi.meoipzi.shortform.dto.ShortformUpdateRequestDTO;
import meoipzi.meoipzi.shortform.repository.ShortFormRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final ShortFormRepository shortformRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final S3Config s3Config;

    @Transactional
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

    @Transactional
    // 숏폼 글 등록
    public ResponseEntity<?> saveShortform(ShortformRequestDTO shortformRequestDTO) {
        User user = userRepository.findByUsername(shortformRequestDTO.getUsername())
                .orElseThrow(() -> new NotFoundMemberException("사용자를 찾을 수 없습니다: " + shortformRequestDTO.getUsername()));

        try {
            if (shortformRequestDTO.getImgUrl() != null) {
                String filePath = s3Config.upload(shortformRequestDTO.getImgUrl());
                ShortForm shortform = shortformRequestDTO.toEntity();
                shortform.setImgUrl(filePath);
                shortformRepository.save(shortformRequestDTO.toEntity());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(shortformRequestDTO);
    }

    // 숏폼 글 삭제
    @Transactional
    public void deleteShortform(Long communityId) {
        shortformRepository.deleteById(communityId); // 글 삭제
    }

    // 숏폼 글 수정
    @Transactional
    public void updateShortform(Long shortformId, ShortformUpdateRequestDTO shortformUpdateRequestDTO) throws IOException {

        ShortForm shortform = shortformRepository.findById(shortformId)
                .orElseThrow(() -> new RuntimeException("숏폼 게시글을 찾을 수 없습니다."));
        Optional<ShortForm> sf = shortformRepository.findById(shortformId);
        if (sf.isPresent()) {
            ShortForm originalShortform = sf.get();

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
            ShortForm shortform = shortformRepository.findById(communityId)
                    .orElseThrow(()-> new NoSuchElementException("조회할 숏폼이 존재하지 않습니다."));

            return new ShortformResponseDTO(shortform);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "조회할 숏폼이 존재하지 않습니다.");
        }
    }

}