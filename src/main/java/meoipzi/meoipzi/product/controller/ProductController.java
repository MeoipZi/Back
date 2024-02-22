package meoipzi.meoipzi.product.controller;

import lombok.RequiredArgsConstructor;
import meoipzi.meoipzi.product.dto.ProductRequestDTO;
import meoipzi.meoipzi.product.dto.ProductResponseDTO;
import meoipzi.meoipzi.product.service.ProductService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    //상품 등록하기
    @PostMapping("/products")
    public void saveProduct(ProductRequestDTO productRequestDTO){
        productService.join(productRequestDTO);
    }

     // [제품 정보 클릭 부분]         1개 상품 조회 화면
    @GetMapping("/products/{productId}")
    public ProductResponseDTO findOneProduct(@PathVariable("productId")Long productId){
        return productService.findOneProduct(productId);
    }

    //상품 삭제하기
    @DeleteMapping("/products/{productId}")
    public String deleteComment(@PathVariable("productId") Long productId){
        productService.deleteProduct(productId);
        return "삭제";
    }


}
