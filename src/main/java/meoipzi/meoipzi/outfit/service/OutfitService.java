package meoipzi.meoipzi.outfit.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import meoipzi.meoipzi.config.S3Config;
import meoipzi.meoipzi.login.domain.User;
import meoipzi.meoipzi.login.repository.UserRepository;
import meoipzi.meoipzi.outfit.domain.Outfit;
import meoipzi.meoipzi.outfit.dto.OutfitRequestDTO;
import meoipzi.meoipzi.outfit.dto.OutfitResponseDTO;
import meoipzi.meoipzi.outfit.dto.OutfitUpdateRequestDTO;
import meoipzi.meoipzi.product.dto.ProductListResponseDTO;
import meoipzi.meoipzi.outfit.repository.OutfitRepository;
import meoipzi.meoipzi.product.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

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
    public void join (OutfitRequestDTO outfitRequestDTO){
        try{
            if(outfitRequestDTO.getImgUrl() != null){
                String filePath = s3Config.upload(outfitRequestDTO.getImgUrl());
                outfitRequestDTO.toEntity().setImgUrl(filePath); //Outfit 객체에 대한 것이니까 스트링 타입으로 넣어야함
            }
            // 토큰을 여기서??
            Long userId = outfitRequestDTO.getUserId();
            //System.out.println(userId);
            Optional<User> user = userRepository.findById(userId);
//이 userId가 관리자 권한을 포함하는가??? 로직만들어야함.

            Outfit outfit = outfitRequestDTO.toEntity();
            outfit.setUser(user.get());

            outfitRepository.save(outfit);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public void updateOutfit(Long outfitId, OutfitUpdateRequestDTO outfitUpdateRequestDTO) {
        try {
            Optional<Outfit> optionalOutfit = outfitRepository.findById(outfitId);
            if (optionalOutfit.isPresent()) {
                Outfit existingOutfit = optionalOutfit.get();

                if (outfitUpdateRequestDTO.getImgUrl() != null) {
                    String filePath = s3Config.upload(outfitUpdateRequestDTO.getImgUrl());
                    existingOutfit.setImgUrl(filePath);
                }


                existingOutfit.setContent(outfitUpdateRequestDTO.getContent());
                existingOutfit.setModelHeight(outfitUpdateRequestDTO.getModelHeight());
                existingOutfit.setModelWeight(outfitUpdateRequestDTO.getModelWeight());
                existingOutfit.setModelInstagramId(outfitUpdateRequestDTO.getModelInstagramId());
                existingOutfit.setModelGender(outfitUpdateRequestDTO.getModelGender());


                outfitRepository.save(existingOutfit);
            } else {
                throw new EntityNotFoundException("Outfit not found with id: " + outfitId);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to update outfit: " + e.getMessage(), e);
        }
    }

    @Transactional
    public void deleteOutfit(Long outfitId){
        outfitRepository.deleteById(outfitId);
    }

    public OutfitResponseDTO clickOutfit(Long outfitId){
        Outfit outfit = outfitRepository.findById(outfitId).orElse(null);
        List<ProductListResponseDTO> productListResponseDTOS = productService.getProductsByOutfitId(outfitId);

        OutfitResponseDTO outfitResponseDTO = new OutfitResponseDTO(outfit);
        outfitResponseDTO.setProducts(productListResponseDTOS);

        return outfitResponseDTO;
    }

}
