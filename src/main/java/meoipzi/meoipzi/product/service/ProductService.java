package meoipzi.meoipzi.product.service;

import lombok.RequiredArgsConstructor;
import meoipzi.meoipzi.config.S3Config;
import meoipzi.meoipzi.login.domain.User;
import meoipzi.meoipzi.login.repository.UserRepository;
import meoipzi.meoipzi.outfit.domain.Outfit;
import meoipzi.meoipzi.product.domain.Product;
import meoipzi.meoipzi.product.dto.ProductListResponseDTO;
import meoipzi.meoipzi.product.dto.ProductRequestDTO;
import meoipzi.meoipzi.product.dto.ProductResponseDTO;
import meoipzi.meoipzi.outfit.repository.OutfitRepository;
import meoipzi.meoipzi.product.repository.ProductRepository;
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

    private final UserRepository userRepository;
    private final S3Config s3Config;
    private final OutfitRepository outfitRepository;

    @Transactional
    public void join(ProductRequestDTO productRequestDTO){
        try{
            if(productRequestDTO.getImgUrl() != null){
                String filePath = s3Config.upload(productRequestDTO.getImgUrl());
                productRequestDTO.toEntity().setImgUrl(filePath); // 이걸로 바꾸기!
            }
            Long userId = productRequestDTO.getUserId();
            Optional<User> user = userRepository.findById(userId);

            Long outfitId = productRequestDTO.getOutfitId();
            Optional<Outfit> outfit = outfitRepository.findById(outfitId);

            Product product = productRequestDTO.toEntity(); //DTO를 엔티티로 변환
            product.setUser(user.get());
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

    @Transactional
    public void deleteProduct(Long productId){
        productRepository.deleteById(productId);
    }
}
