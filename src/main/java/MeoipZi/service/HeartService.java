package MeoipZi.service;

import MeoipZi.Exception.NotFoundContentException;
import MeoipZi.Exception.NotFoundMemberException;
import MeoipZi.domain.Community;
import MeoipZi.domain.Heart;
import MeoipZi.domain.ShortForm;
import MeoipZi.domain.User;
import MeoipZi.dto.HeartRequestDto;
import MeoipZi.repository.CommunityRepository;
import MeoipZi.repository.HeartRepository;
import MeoipZi.repository.ShortformRepository;
import MeoipZi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class HeartService {
    private final HeartRepository heartRepository;
    private final UserRepository userRepository;
    private final CommunityRepository communityRepository;
    private final ShortformRepository shortformRepository;

    @Transactional
    public ResponseEntity<?> likeHeart(HeartRequestDto requestDto) throws Exception{

        User user = userRepository.findByUsername(requestDto.getUsername())
                .orElseThrow(() -> new NotFoundMemberException("Could not found username : " + requestDto.getUsername()));

        switch (requestDto.getContentType()) {
            case "shortform" -> {
                ShortForm shortform = shortformRepository.findById(requestDto.getContentId())
                        .orElseThrow(() -> new NotFoundContentException("Could not found shortform : " + requestDto.getContentId()));
                if (heartRepository.findByUserAndShortForm(user, shortform).isPresent()) {
                    Heart heart = heartRepository.findByUserAndShortForm(user, shortform)
                            .orElseThrow(() -> new NotFoundContentException("Could not found heart"));
                    heartRepository.delete(heart);
                    shortform.setLikesCount(shortform.getLikesCount() - 1);
                } else {
                    Heart sfheart = Heart.builder()
                            .user(user)
                            .shortForm(shortform)
                            .contentType(requestDto.getContentType())
                            .createdAt(LocalDateTime.now())
                            .build();
                    heartRepository.save(sfheart);
                    shortform.setLikesCount(shortform.getLikesCount() + 1);
                }
            }
            case "community" -> {
                Community community = communityRepository.findById(requestDto.getContentId())
                        .orElseThrow(() -> new NotFoundContentException("Could not found community board : " + requestDto.getContentId()));
                if (heartRepository.findByUserAndCommunity(user, community).isPresent()) {
                    Heart heart = heartRepository.findByUserAndCommunity(user, community)
                            .orElseThrow(() -> new NotFoundContentException("Could not found heart"));
                    heartRepository.delete(heart);
                    community.setLikesCount(community.getLikesCount() - 1);
                } else {
                    Heart cmheart = Heart.builder()
                            .user(user)
                            .community(community)
                            .contentType(requestDto.getContentType())
                            .createdAt(LocalDateTime.now())
                            .build();
                    heartRepository.save(cmheart);
                    community.setLikesCount(community.getLikesCount() + 1);
                }
            }
 /*           case "outfit" -> {
                Outfit outfit = outfitRepository.findById(requestDto.getContentId())
                        .orElseThrow(() -> new NotFoundContentException("Could not found outfit : " + requestDto.getContentId()));
                if (heartRepository.findByUserAndOutfit(user, outfit).isPresent()) {
                    Heart heart = heartRepository.findByUserAndOutfit(user, outfit)
                            .orElseThrow(() -> new NotFoundContentException("Could not found heart"));
                    heartRepository.delete(heart);
                    outfit.setLikesCount(outfit.getLikesCount() - 1);
                } else {
                    Heart ofheart = Heart.builder()
                            .user(user)
                            .outfit(outfit)
                            .contentType(requestDto.getContentType())
                            .createdAt(LocalDateTime.now())
                            .build();
                    heartRepository.save(ofheart);
                    outfit.setLikesCount(outfit.getLikesCount() + 1);
                }
            } */
        }
        return ResponseEntity.ok(requestDto);
    }
}
