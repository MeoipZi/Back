package meoipzi.meoipzi.controller;

import lombok.RequiredArgsConstructor;
import meoipzi.meoipzi.dto.ProductRequestDTO;
import meoipzi.meoipzi.dto.ProductResponseDTO;
import meoipzi.meoipzi.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
