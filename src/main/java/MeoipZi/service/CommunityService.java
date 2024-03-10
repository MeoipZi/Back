package MeoipZi.service;


import MeoipZi.dto.CommunityDto.*;
import MeoipZi.Exception.NotFoundMemberException;
import MeoipZi.config.S3Config;
import MeoipZi.domain.CommentCommunity;
import MeoipZi.domain.Community;
import MeoipZi.domain.User;
import MeoipZi.repository.CommentRepository.CommentCommunityRepository;
import MeoipZi.repository.CommunityRepository;
import MeoipZi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommunityService {
    private final CommunityRepository communityRepository;
    private final CommentCommunityRepository commentCommunityRepository;
    private final S3Config s3Config;
    private final UserRepository userRepository;

    // 커뮤니티 게시글 카테고리별, 최신순 정렬 조회
    public Page<CommunityListResponseDTO> getLatestCommunityList(String category, Pageable pageable){
        try {
            Page<Community> communityPage = communityRepository.findAllByCategoryOrderByIdDesc(category, pageable);
            List<CommunityListResponseDTO> communityListResponseDTOS = communityPage.stream()
                    .map(CommunityListResponseDTO::new)
                    .collect(Collectors.toList());
            return new PageImpl<>(communityListResponseDTOS, pageable, communityListResponseDTOS.size());
        } catch(Exception e){
            e.printStackTrace();
            return new PageImpl<>(Collections.emptyList());
        }
    }

    // 커뮤니티 게시글 카테고리별, 좋아요순 정렬 조회
    public Page<CommunityListResponseDTO> getPopularCommunintyList(String category, Pageable pageable){
        try {
            Page<Community> communityPage = communityRepository.findAllByCategoryOrderByLikesCount(category, pageable);
            List<CommunityListResponseDTO> communityListResponseDTOS = communityPage.stream()
                    .map(CommunityListResponseDTO::new)
                    .collect(Collectors.toList());
            return new PageImpl<>(communityListResponseDTOS, pageable, communityListResponseDTOS.size());
        } catch(Exception e){
            e.printStackTrace();
            return new PageImpl<>(Collections.emptyList());
        }
    }

    // 커뮤니티 글 등록
    @Transactional
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
    public ResponseEntity<?> updateCommunity(Long communityId, CommunityUpdateRequestDTO communityUpdateRequestDTO) throws IOException {
        User user = userRepository.findByUsername(communityUpdateRequestDTO.getUserName())
                .orElseThrow(()-> new NotFoundMemberException("해당 사용자를 찾을 수 없습니다."));

        try {
            Community originalCommunity = communityRepository.findById(communityId)
                    .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));

            if (communityUpdateRequestDTO.getImgUrl() != null) {
                String filePath = s3Config.upload(communityUpdateRequestDTO.getImgUrl());
                originalCommunity.setImgUrl(filePath);
            }
            originalCommunity.setCategory(communityUpdateRequestDTO.getCategory());
            originalCommunity.setTitle(communityUpdateRequestDTO.getTitle());
            originalCommunity.setContents(communityUpdateRequestDTO.getContents());
            communityRepository.save(originalCommunity);
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(communityUpdateRequestDTO);
    }

    // 커뮤니티 글 하나 상세 조회
    // + 추후에 해당 글에 해당하는 댓글 목록까지 함께 조회되어야 함!!!! -> 구현 완료
    @Transactional
    public CommunityResponseDTO viewCommunity(Long communityId){
      try {
          // 조회할 커뮤니티 글이 존재하는지 확인
          Community community = communityRepository.findById(communityId)
                  .orElseThrow(()-> new NoSuchElementException("조회할 커뮤니티 게시글이 존재하지 않습니다."));

          // 해당 커뮤니티 글에 대한 댓글 목록 조회
          List<CommentCommunity> comments = commentCommunityRepository.findByCommunityId(communityId);

          return new CommunityResponseDTO(community, comments);
      } catch (NoSuchElementException e) {
          throw new ResponseStatusException(HttpStatus.NOT_FOUND, "조회할 게시글이 존재하지 않습니다.");
      }
    }
}
