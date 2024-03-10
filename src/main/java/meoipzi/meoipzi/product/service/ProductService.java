package meoipzi.meoipzi.product.service;

import lombok.RequiredArgsConstructor;
import meoipzi.meoipzi.common.config.S3Config;
import meoipzi.meoipzi.common.excepiton.NotFoundMemberException;
import meoipzi.meoipzi.login.domain.User;
import meoipzi.meoipzi.login.repository.UserRepository;
import meoipzi.meoipzi.profile.outfit.domain.Outfit;
import meoipzi.meoipzi.product.domain.Product;
import meoipzi.meoipzi.product.dto.ProductListResponseDTO;
import meoipzi.meoipzi.product.dto.ProductRequestDTO;
import meoipzi.meoipzi.product.dto.ProductResponseDTO;
import meoipzi.meoipzi.profile.outfit.repository.OutfitRepository;
import meoipzi.meoipzi.product.repository.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    private final UserRepository userRepository;
    private final S3Config s3Config;
    private final OutfitRepository outfitRepository;

    @Transactional
    public ResponseEntity<?> saveProduct(ProductRequestDTO productRequestDTO){
        User user = userRepository.findByUsername(productRequestDTO.getUsername())
                .orElseThrow(() -> new NotFoundMemberException("Could not found username : " + productRequestDTO.getUsername()));


        Outfit outfit = outfitRepository.findById( productRequestDTO.getOutfitId())
                .orElseThrow(() -> new NotFoundMemberException("Could not found outfit : " + productRequestDTO.getOutfitId()));


        try{
            if(productRequestDTO.getImgUrl() != null){
                String filePath = s3Config.upload(productRequestDTO.getImgUrl());
                Product product = productRequestDTO.toEntity(user); //DTO를 엔티티로 변환
                product.setImgUrl(filePath);
                product.setOutfit(outfit);
                productRepository.save(product);
            }

        }catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(productRequestDTO);
    }

    public ProductResponseDTO findOneProduct(Long productId){
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("해당 ID의 제품을 찾을 수 없습니다."));

        return new ProductResponseDTO(product);
    }

    //outfitId와 같은 3개의 상품 가져오기 // service에 위치함.
    public List<ProductListResponseDTO> getProductsByOutfitId(Long outfitId){

        List<ProductListResponseDTO> productListResponseDTOS = new ArrayList<>();

        List<Product> products  = productRepository.findByOutfitId(outfitId);
        for(Product product: products){
            ProductListResponseDTO productListResponseDTO = new ProductListResponseDTO(product);
            productListResponseDTOS.add(productListResponseDTO);
        }
        return productListResponseDTOS;
    }

    @Transactional
    public void deleteProduct(Long productId){
        productRepository.deleteById(productId);
    }
}
