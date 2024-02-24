package MeoipZi.meoipzi.service;


import MeoipZi.meoipzi.config.S3Config;
import MeoipZi.meoipzi.domain.Community;
import MeoipZi.meoipzi.domain.User;
import MeoipZi.meoipzi.dto.CommunityDto.CommunityListResponseDTO;
import MeoipZi.meoipzi.dto.CommunityDto.CommunityRequestDTO;
import MeoipZi.meoipzi.dto.CommunityDto.CommunityResponseDTO;
import MeoipZi.meoipzi.dto.CommunityDto.CommunityUpdateRequestDTO;
import MeoipZi.meoipzi.dto.UserDto;
import MeoipZi.meoipzi.repository.CommunityRepository;
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
public class CommunityService {
    private final CommunityRepository communityRepository;
    private final UserService userService;
    private final S3Config s3Config;
    private final UserRepository userRepository;

    // 모든 커뮤니티 글 리스트 조회
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

    // 커뮤니티 글 등록
    public void join(CommunityRequestDTO communityRequestDTO) {
        try {
            if (communityRequestDTO.getImgUrl() != null) {
                String filePath = s3Config.upload(communityRequestDTO.getImgUrl());
                communityRequestDTO.toEntity().setImgUrl(filePath);
            }
            Long userId = communityRequestDTO.getUserId();

            Optional<User> user = userRepository.findById(userId);

            Community community = communityRequestDTO.toEntity();
            community.setUser(user.get());

            communityRepository.save(communityRequestDTO.toEntity());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
