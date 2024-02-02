package meoipzi.meoipzi.service;

import lombok.RequiredArgsConstructor;
import meoipzi.meoipzi.config.S3Config;
import meoipzi.meoipzi.domain.Outfit;
import meoipzi.meoipzi.dto.OutfitRequestDTO;
import meoipzi.meoipzi.dto.OutfitResponseDTO;
import meoipzi.meoipzi.dto.ProductListResponseDTO;
import meoipzi.meoipzi.dto.ProductResponseDTO;
import meoipzi.meoipzi.repository.OutfitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OutfitService {
    private final OutfitRepository outfitRepository;
    private final S3Config s3Config;

    private final ProductService productService;
    //코디 등록
    @Transactional
    public void join (OutfitRequestDTO outfitRequestDTO){
        try{
            if(outfitRequestDTO.getImgUrl() != null){
                String filePath = s3Config.upload(outfitRequestDTO.getImgUrl());
                outfitRequestDTO.toEntity().setImgUrl(filePath); // 이걸로 바꾸기!
            }
            outfitRepository.save(outfitRequestDTO.toEntity());
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public OutfitResponseDTO clickOutfit(Long outfitId){
        Outfit outfit = outfitRepository.findById(outfitId).orElse(null);
        List<ProductListResponseDTO> productListResponseDTOS = productService.getProductsByOutfitId(outfitId);

        OutfitResponseDTO outfitResponseDTO = new OutfitResponseDTO(outfit);
        outfitResponseDTO.setProducts(productListResponseDTOS);

        return outfitResponseDTO;
    }

}
