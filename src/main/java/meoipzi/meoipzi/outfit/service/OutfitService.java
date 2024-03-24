package meoipzi.meoipzi.outfit.service;

import lombok.RequiredArgsConstructor;
import meoipzi.meoipzi.common.config.S3Config;
import meoipzi.meoipzi.common.excepiton.NotFoundMemberException;
import meoipzi.meoipzi.login.domain.User;
import meoipzi.meoipzi.login.repository.UserRepository;
import meoipzi.meoipzi.outfit.dto.OutfitResponseDTO;
import meoipzi.meoipzi.outfit.dto.OutfitTotalResponseDTO;
import meoipzi.meoipzi.outfit.dto.OutfitUpdateRequestDTO;
import meoipzi.meoipzi.outfit.domain.Outfit;
import meoipzi.meoipzi.outfit.dto.OutfitRequestDTO;
import meoipzi.meoipzi.product.dto.ProductListResponseDTO;
import meoipzi.meoipzi.outfit.repository.OutfitRepository;
import meoipzi.meoipzi.product.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OutfitService {
    private final OutfitRepository outfitRepository;
    private final UserRepository userRepository;
    private final S3Config s3Config;

    private final ProductService productService;

    //코디 등록
    @Transactional
    public ResponseEntity<?> saveOutfit(OutfitRequestDTO outfitRequestDTO) throws Exception{
        User user = userRepository.findByUsername(outfitRequestDTO.getUsername())
                .orElseThrow(() -> new NotFoundMemberException("Could not found username : " + outfitRequestDTO.getUsername()));//status code 500

        try{
            if(outfitRequestDTO.getImgUrl() != null){
                String filePath = s3Config.upload(outfitRequestDTO.getImgUrl());
                Outfit outfit = outfitRequestDTO.toEntity(user);
                //outfitRequestDTO.toEntity(user).setImgUrl(filePath); //Outfit 객체에 대한 것이니까 스트링 타입으로 넣어야함
                outfit.setImgUrl(filePath);
                outfitRepository.save(outfit);
            }

        }catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(outfitRequestDTO);
    }

    @Transactional
    public  ResponseEntity<?>  updateOutfit(Long outfitId, OutfitUpdateRequestDTO outfitUpdateRequestDTO) {
        User user = userRepository.findByUsername(outfitUpdateRequestDTO.getUsername())
                .orElseThrow(() -> new NotFoundMemberException("Could not found username : " + outfitUpdateRequestDTO.getUsername()));

        try {
            Outfit existingOutfit = outfitRepository.findById(outfitId)
                    .orElseThrow(() -> new RuntimeException("해당 ID의 제품을 찾을 수 없습니다."));

            if (outfitUpdateRequestDTO.getImgUrl() != null) {
                String filePath = s3Config.upload(outfitUpdateRequestDTO.getImgUrl());
                existingOutfit.setImgUrl(filePath);
            }
            existingOutfit.setContent(outfitUpdateRequestDTO.getContent());
            outfitRepository.save(existingOutfit);
        } catch (IOException e) {
                throw new RuntimeException("Failed to update outfit: " + e.getMessage(), e);
            }
        return ResponseEntity.ok(outfitUpdateRequestDTO);
    }

    @Transactional
    public void deleteOutfit(Long outfitId){
        outfitRepository.deleteById(outfitId);
    }

    public OutfitResponseDTO clickOutfit(Long outfitId){
        Outfit outfit = outfitRepository.findById(outfitId)
                .orElseThrow(() -> new RuntimeException("해당 ID의 제품을 찾을 수 없습니다."));
        List<ProductListResponseDTO> productListResponseDTOS = productService.getProductsByOutfitId(outfitId);

        OutfitResponseDTO outfitResponseDTO = new OutfitResponseDTO(outfit);
        outfitResponseDTO.setProducts(productListResponseDTOS);

        return outfitResponseDTO;
    }


    public Page<OutfitTotalResponseDTO> getLatestOutfits(Pageable pageable) {
        try {
            Page<Outfit> outfitsPage = outfitRepository.findAllByOrderByIdDesc(pageable);
            List<OutfitTotalResponseDTO> outfitDTOs = outfitsPage.stream()
                    .map(OutfitTotalResponseDTO::new)
                    .collect(Collectors.toList());
            return new PageImpl<>(outfitDTOs, pageable, outfitDTOs.size());
        } catch (Exception e) {
            e.printStackTrace();
            // 예외 처리 또는 로깅을 통한 추가 조사
            return new PageImpl<>(Collections.emptyList());
        }
    }

    public Page<OutfitTotalResponseDTO> getPopularOutfits(Pageable pageable){
        try {
            Page<Outfit> outfitsPage= outfitRepository.findByOrderByLikesCountDesc(pageable);
            List<OutfitTotalResponseDTO> outfitDTOs = outfitsPage.stream()
                    .map(OutfitTotalResponseDTO::new)
                    .collect(Collectors.toList());
            return new PageImpl<>(outfitDTOs, pageable, outfitsPage.getTotalElements());
        } catch (Exception e) {
            e.printStackTrace();
            // 예외 처리 또는 로깅을 통한 추가 조사
            return new PageImpl<>(Collections.emptyList());
        }
    }


}