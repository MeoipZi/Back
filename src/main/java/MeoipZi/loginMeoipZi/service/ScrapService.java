package MeoipZi.loginMeoipZi.service;

import MeoipZi.loginMeoipZi.domain.*;
import MeoipZi.loginMeoipZi.dto.ScrapRequestDto;
import MeoipZi.loginMeoipZi.exception.NotFoundContentException;
import MeoipZi.loginMeoipZi.exception.NotFoundMemberException;
import MeoipZi.loginMeoipZi.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ScrapService {

    private final ScrapRepository scrapRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OutfitRepository outfitRepository;

    @Transactional
    public ResponseEntity<?> scrapContent(ScrapRequestDto requestDto) throws Exception{

        User user = userRepository.findByUsername(requestDto.getUsername())
                .orElseThrow(() -> new NotFoundMemberException("Could not found username : " + requestDto.getUsername()));

        if (requestDto.getContentType().equals("outfit")) {
            Outfit outfit = outfitRepository.findById(requestDto.getContentId())
                    .orElseThrow(() -> new NotFoundContentException("Could not found outfit : " + requestDto.getContentId()));
            if (scrapRepository.findByUserAndOutfit(user, outfit).isPresent()) {
                Scrap scrap = scrapRepository.findByUserAndOutfit(user, outfit)
                        .orElseThrow(() -> new NotFoundContentException("Could not found scrap"));
            } else {
                Scrap scrap = Scrap.builder()
                        .user(user)
                        .outfit(outfit)
                        .contentType(requestDto.getContentType())
                        .createdAt(LocalDateTime.now())
                        .build();
            }
        } else if (requestDto.getContentType().equals("product")) {
            Product product = productRepository.findById(requestDto.getContentId())
                    .orElseThrow(() -> new NotFoundContentException("Could not found product : " + requestDto.getContentId()));
            if (scrapRepository.findByUserAndProduct(user, product).isPresent()) {
                Scrap scrap = scrapRepository.findByUserAndProduct(user, product)
                        .orElseThrow(() -> new NotFoundContentException("Could not found Scrap"));
                scrapRepository.delete(scrap);
            } else {
                Scrap scrap = Scrap.builder()
                        .user(user)
                        .product(product)
                        .contentType(requestDto.getContentType())
                        .createdAt(LocalDateTime.now())
                        .build();
                scrapRepository.save(scrap);
            }

        }
        return ResponseEntity.ok(requestDto);
    }
}
