package meoipzi.meoipzi.scrap.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import meoipzi.meoipzi.common.excepiton.NotFoundMemberException;
import meoipzi.meoipzi.heart.exception.NotFoundContentException;
import meoipzi.meoipzi.login.domain.User;
import meoipzi.meoipzi.login.repository.UserRepository;
import meoipzi.meoipzi.outfit.domain.Outfit;
import meoipzi.meoipzi.outfit.repository.OutfitRepository;
import meoipzi.meoipzi.product.domain.Product;
import meoipzi.meoipzi.product.repository.ProductRepository;
import meoipzi.meoipzi.scrap.domain.Scrap;
import meoipzi.meoipzi.scrap.dto.ScrapRequestDto;
import meoipzi.meoipzi.scrap.repository.ScrapRepository;
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
                scrapRepository.delete(scrap);
            } else {
                Scrap scrap = Scrap.builder()
                        .user(user)
                        .outfit(outfit)
                        .contentType(requestDto.getContentType())
                        .createdAt(LocalDateTime.now())
                        .build();
                scrapRepository.save(scrap);
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