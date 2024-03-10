package meoipzi.meoipzi.heart.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import meoipzi.meoipzi.common.excepiton.NotFoundMemberException;
import meoipzi.meoipzi.community.domain.Community;
import meoipzi.meoipzi.community.repository.CommunityRepository;
import meoipzi.meoipzi.heart.domain.Heart;
import meoipzi.meoipzi.heart.dto.HeartRequestDto;
import meoipzi.meoipzi.heart.exception.NotFoundContentException;
import meoipzi.meoipzi.heart.repository.HeartRepository;
import meoipzi.meoipzi.login.domain.User;
import meoipzi.meoipzi.login.repository.UserRepository;
import meoipzi.meoipzi.outfit.domain.Outfit;
import meoipzi.meoipzi.outfit.repository.OutfitRepository;
import meoipzi.meoipzi.shortform.domain.ShortForm;
import meoipzi.meoipzi.shortform.repository.ShortFormRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class HeartService {

    private final HeartRepository heartRepository;
    private final UserRepository userRepository;
    private final CommunityRepository communityRepository;
    private final ShortFormRepository shortFormRepository;
    private final OutfitRepository outfitRepository;

    @Transactional
    public ResponseEntity<?> likeHeart(HeartRequestDto requestDto) throws Exception{

        User user = userRepository.findByUsername(requestDto.getUsername())
                .orElseThrow(() -> new NotFoundMemberException("Could not found username : " + requestDto.getUsername()));

        switch (requestDto.getContentType()) {
            case "shortform" -> {
                ShortForm shortform = shortFormRepository.findById(requestDto.getContentId())
                        .orElseThrow(() -> new NotFoundContentException("Could not found shortform : " + requestDto.getContentId()));
                if (heartRepository.findByUserAndShortForm(user, shortform).isPresent()) {
                    Heart heart = heartRepository.findByUserAndShortForm(user, shortform)
                            .orElseThrow(() -> new NotFoundContentException("Could not found heart"));
                    heartRepository.delete(heart);
                    shortform.setLikes_count(shortform.getLikes_count() - 1);
                } else {
                    Heart sfheart = Heart.builder()
                            .user(user)
                            .shortForm(shortform)
                            .contentType(requestDto.getContentType())
                            .createdAt(LocalDateTime.now())
                            .build();
                    heartRepository.save(sfheart);
                    shortform.setLikes_count(shortform.getLikes_count() + 1);
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
            case "outfit" -> {
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
            }
        }
        return ResponseEntity.ok(requestDto);
    }
}