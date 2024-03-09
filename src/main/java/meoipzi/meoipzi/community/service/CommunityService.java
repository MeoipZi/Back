package meoipzi.meoipzi.community.service;

import lombok.RequiredArgsConstructor;
import meoipzi.meoipzi.common.config.S3Config;
import meoipzi.meoipzi.common.excepiton.NotFoundMemberException;
import meoipzi.meoipzi.community.domain.Community;
import meoipzi.meoipzi.community.dto.CommunityListResponseDTO;
import meoipzi.meoipzi.community.dto.CommunityRequestDTO;
import meoipzi.meoipzi.community.dto.CommunityResponseDTO;
import meoipzi.meoipzi.community.dto.CommunityUpdateRequestDTO;
import meoipzi.meoipzi.community.repository.CommunityRepository;
import meoipzi.meoipzi.login.domain.User;
import meoipzi.meoipzi.login.repository.UserRepository;
import meoipzi.meoipzi.login.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
public class CommunityService {
    private final CommunityRepository communityRepository;
    private final UserService userService;
    private final S3Config s3Config;
    private final UserRepository userRepository;

    @Transactional
    // 모든 커뮤니티 글 리스트 조회 + 로그인 여부 확인
    public List<CommunityListResponseDTO> getCommunityList() {
        try {
            List<CommunityListResponseDTO> communityList = communityRepository.findAll()
                    .stream()
                    .map(CommunityListResponseDTO::new)
                    .collect(Collectors.toList());

            return communityList;
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @Transactional
    // 커뮤니티 글 등록
    public ResponseEntity<?> saveCommunity(CommunityRequestDTO communityRequestDTO) {
        User user = userRepository.findByUsername(communityRequestDTO.getUserName())
                .orElseThrow(()-> new NotFoundMemberException("해당 이메일에 해당하는 회원이 없습니다. : "+ communityRequestDTO.getUserName()));

        try {
            if(communityRequestDTO.getImgUrl() != null) {
                String filePath = s3Config.upload(communityRequestDTO.getImgUrl());
                Community community = communityRequestDTO.toEntity();
                community.setImgUrl(filePath);
                communityRepository.save(community);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(communityRequestDTO);
    }

    // 커뮤니티 글 삭제
    @Transactional
    public void deleteCommunity(Long communityId){
        communityRepository.deleteById(communityId); // 글 삭제
    }

    // 커뮤니티 글 수정
    @Transactional
    public void updateCommunity(Long communityId, CommunityUpdateRequestDTO communityUpdateRequestDTO) throws IOException {
        Community community = communityRepository.findById(communityId)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));

        Optional<Community> op = communityRepository.findById(communityId);

        if (op.isPresent()) {
            Community originalCommunity = op.get();

            if (communityUpdateRequestDTO.getImgUrl() != null) {
                String filePath = s3Config.upload(communityUpdateRequestDTO.getImgUrl());
                originalCommunity.setImgUrl(filePath);
            }

            originalCommunity.setCategory(communityUpdateRequestDTO.getCategory());
            originalCommunity.setTitle(communityUpdateRequestDTO.getTitle());
            originalCommunity.setContents(communityUpdateRequestDTO.getContents());

            communityRepository.save(originalCommunity);
        }
    }

    // 커뮤니티 글 하나 상세 조회
    @Transactional
    public CommunityResponseDTO viewCommunity(Long communityId){
        try {
            // 조회할 커뮤니티글이 존재하는지 확인
            Community community = communityRepository.findById(communityId)
                    .orElseThrow(()-> new NoSuchElementException("조회할 커뮤니티 게시글이 존재하지 않습니다."));

            return new CommunityResponseDTO(community);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "조회할 게시글이 존재하지 않습니다.");
        }
    }
}