package meoipzi.meoipzi.product.controller;

import lombok.RequiredArgsConstructor;
import meoipzi.meoipzi.login.domain.User;
import meoipzi.meoipzi.product.dto.ProductRequestDTO;
import meoipzi.meoipzi.product.dto.ProductResponseDTO;
import meoipzi.meoipzi.product.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    //상품 등록하기 - 로그인여부 확인
    @PostMapping("/products")
    public ResponseEntity<?> saveProduct(ProductRequestDTO productRequestDTO) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        productRequestDTO.setUsername(authentication.getName());

        if (authentication.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"))) {
            // 사용자에게 ROLE_ADMIN이 할당되어 있을 경우
            return productService.saveProduct(productRequestDTO);
        } else {
            // 사용자에게 ROLE_ADMIN이 할당되어 있지 않은 경우
            return new ResponseEntity<>("Permission denied", HttpStatus.FORBIDDEN);
        }

    }


    // [제품 정보 클릭 부분]         1개 상품 조회 화면 - 로그인여부랑 상관없음
     @GetMapping("/products/{productId}")
     public ResponseEntity<?> findOneProduct(@PathVariable("productId") Long productId) {
         try {
             ProductResponseDTO productResponseDTO = productService.findOneProduct(productId);
             return new ResponseEntity<>(productResponseDTO, HttpStatus.OK);
         } catch (Exception e) {
             return new ResponseEntity<>("Failed to retrieve product", HttpStatus.INTERNAL_SERVER_ERROR);
         }
     }


    //상품 삭제하기 - 로그인 여부확인
    @DeleteMapping("/products/{productId}")
    public ResponseEntity<?> deleteComment(@PathVariable("productId") Long productId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"))) {
            // 사용자에게 ROLE_ADMIN이 할당되어 있을 경우
            try {
                productService.deleteProduct(productId);
                return new ResponseEntity<>("Product successfully deleted", HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>("Failed to delete product", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            // 사용자에게 ROLE_ADMIN이 할당되어 있지 않은 경우
            return new ResponseEntity<>("Permission denied", HttpStatus.FORBIDDEN);
        }
    }



}
