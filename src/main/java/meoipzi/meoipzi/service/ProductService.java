package meoipzi.meoipzi.service;

import lombok.RequiredArgsConstructor;
import meoipzi.meoipzi.config.S3Config;
import meoipzi.meoipzi.domain.Outfit;
import meoipzi.meoipzi.domain.Product;
import meoipzi.meoipzi.dto.ProductListResponseDTO;
import meoipzi.meoipzi.dto.ProductRequestDTO;
import meoipzi.meoipzi.dto.ProductResponseDTO;
import meoipzi.meoipzi.repository.OutfitRepository;
import meoipzi.meoipzi.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final S3Config s3Config;
    private final OutfitRepository outfitRepository;

    @Transactional
    public void join(ProductRequestDTO productRequestDTO){
        try{
            if(productRequestDTO.getImgUrl() != null){
                String filePath = s3Config.upload(productRequestDTO.getImgUrl());
                productRequestDTO.toEntity().setImgUrl(filePath); // 이걸로 바꾸기!
            }
            Product product = productRequestDTO.toEntity(); //DTO를 엔티티로 변환
            Optional<Outfit> outfit = outfitRepository.findById(productRequestDTO.getOutfitId());
            product.setOutfit(outfit.get());

            productRepository.save(product);

        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ProductResponseDTO findOneProduct(Long productId){
        Optional<Product> product = productRepository.findById(productId);
        return new ProductResponseDTO(product.get());
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
}
