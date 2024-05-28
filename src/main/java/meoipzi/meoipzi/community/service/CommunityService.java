package meoipzi.meoipzi.community.service;

import lombok.RequiredArgsConstructor;
import meoipzi.meoipzi.comment.domain.CommentCommunity;
import meoipzi.meoipzi.comment.repository.CommentCommunityRepository;
import meoipzi.meoipzi.common.config.S3Config;
import meoipzi.meoipzi.common.excepiton.NotFoundMemberException;
import meoipzi.meoipzi.community.domain.Community;
import meoipzi.meoipzi.community.domain.Image;
import meoipzi.meoipzi.community.dto.CommunityListResponseDTO;
import meoipzi.meoipzi.community.dto.CommunityRequestDTO;
import meoipzi.meoipzi.community.dto.CommunityResponseDTO;
import meoipzi.meoipzi.community.dto.CommunityUpdateRequestDTO;
import meoipzi.meoipzi.community.repository.CommunityRepository;
import meoipzi.meoipzi.heart.repository.HeartRepository;
import meoipzi.meoipzi.login.domain.User;
import meoipzi.meoipzi.login.repository.UserRepository;
import meoipzi.meoipzi.login.service.UserService;
import meoipzi.meoipzi.shortform.domain.ShortForm;
import meoipzi.meoipzi.shortform.dto.ShortformResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommunityService {
    private final CommunityRepository communityRepository;
    private final CommentCommunityRepository commentCommunityRepository;
    private final S3Config s3Config;
    private final UserRepository userRepository;
    private final HeartRepository heartRepository;


    // 커뮤니티 게시글 카테고리별, 최신순 정렬 조회
    public Page<CommunityListResponseDTO> getLatestCommunityList(String category, Pageable pageable) {
        try {
            Page<Community> communityPage = communityRepository.findAllByCategoryOrderByIdDesc(category, pageable);
            List<CommunityListResponseDTO> communityListResponseDTOS = communityPage.stream()
                    .map(CommunityListResponseDTO::new)
                    .collect(Collectors.toList());
            return new PageImpl<>(communityListResponseDTOS, pageable, communityPage.getTotalElements());
        } catch (Exception e) {
            e.printStackTrace();
            return new PageImpl<>(Collections.emptyList());
        }
    }

    // 커뮤니티 게시글 카테고리별, 좋아요순 정렬 조회
    public Page<CommunityListResponseDTO> getPopularCommunintyList(String category, Pageable pageable) {
        try {
            Page<Community> communityPage = communityRepository.findAllByCategoryOrderByLikesCount(category, pageable);
            List<CommunityListResponseDTO> communityListResponseDTOS = communityPage.stream()
                    .map(CommunityListResponseDTO::new)
                    .collect(Collectors.toList());
            return new PageImpl<>(communityListResponseDTOS, pageable, communityPage.getTotalElements());
        } catch (Exception e) {
            e.printStackTrace();
            return new PageImpl<>(Collections.emptyList());
        }
    }
/**/
    // 커뮤니티 글 등록 -> 등록 시 해당 커뮤니티로 리다이렉트
    @Transactional
    public ResponseEntity<?> saveCommunity(CommunityRequestDTO communityRequestDTO, List<MultipartFile> files) {
        User user = userRepository.findByUsername(communityRequestDTO.getUsername())
                .orElseThrow(()-> new NotFoundMemberException("해당 이메일에 해당하는 회원이 없습니다. : "+ communityRequestDTO.getUsername()));

        Community community = communityRequestDTO.toEntity(user);
        try {
            if(files != null & !files.isEmpty()) {
                for(MultipartFile file : files){
                    String filePath = s3Config.upload(file);
                    Image image = new Image();
                    image.setFilePath(filePath);

                    community.addImage(image);
                }
           }
            communityRepository.save(community);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(viewCommunity(community.getId(), communityRequestDTO.getUsername()));
    }

    // 커뮤니티 글 삭제
    @Transactional
    public void deleteCommunity(Long communityId){
        communityRepository.deleteById(communityId); // 글 삭제
    }

    // 커뮤니티 글 수정
    @Transactional
    public ResponseEntity<?> updateCommunity(Long communityId, CommunityUpdateRequestDTO communityUpdateRequestDTO, List<MultipartFile> files) {
        User user = userRepository.findByUsername(communityUpdateRequestDTO.getUsername())
                .orElseThrow(()-> new NotFoundMemberException("해당 사용자를 찾을 수 없습니다."));

        Community originalCommunity = communityRepository.findById(communityId)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));

        try {
            originalCommunity.getImgUrl().clear();
            if (communityUpdateRequestDTO.getImgUrl() != null) {
                for(MultipartFile file : files){
                    String filePath = s3Config.upload(file);
                    Image image = new Image();
                    image.setFilePath(filePath);
                    originalCommunity.addImage(image);
                }
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
    public CommunityResponseDTO viewCommunity(Long communityId, String username){
        try {
            User user = userRepository.findByUsername(username)
                    .orElseThrow(()-> new NotFoundMemberException("해당 이메일에 해당하는 회원이 없습니다. : "+ username));
            // 조회할 커뮤니티 글이 존재하는지 확인
            Community community = communityRepository.findById(communityId)
                    .orElseThrow(()-> new NoSuchElementException("조회할 커뮤니티 게시글이 존재하지 않습니다."));
            // 해당 커뮤니티 글에 대한 댓글 목록 조회
            List<CommentCommunity> comments = commentCommunityRepository.findByCommunityId(communityId);
            CommunityResponseDTO communityResponseDTO = new CommunityResponseDTO(community, comments);
            if(heartRepository.findByUserAndCommunity(user, community).isPresent())
                communityResponseDTO.setLiked(true);

            return communityResponseDTO;
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "조회할 게시글이 존재하지 않습니다.");
        }
    }
}