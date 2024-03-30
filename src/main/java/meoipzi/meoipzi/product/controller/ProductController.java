package meoipzi.meoipzi.product.controller;

import lombok.RequiredArgsConstructor;
import meoipzi.meoipzi.login.domain.User;
import meoipzi.meoipzi.outfit.dto.OutfitTotalResponseDTO;
import meoipzi.meoipzi.product.dto.ProductRequestDTO;
import meoipzi.meoipzi.product.dto.ProductResponseDTO;
import meoipzi.meoipzi.product.dto.ProductTotalResponseDTO;
import meoipzi.meoipzi.product.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import java.util.stream.Collectors;

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

    //http://localhost:8080/outfits/latest?category=아우터&page=0&size=20
    // 한 카테고리에 어떤 상품들이 있는지 조회하는 화면 [기본 : 최신순 조회]

    @GetMapping("/products/search/category/latest")
    public ResponseEntity<?> getLatestProductsByCategory(@RequestParam(value = "category") String category, @RequestParam(value = "page")int page, @RequestParam(value = "size") int size){
        Pageable pageable = PageRequest.of(page,size);
        Page<ProductTotalResponseDTO> latestProductsPage = productService.getLatestProducts(category, pageable);

        List<List<String>> outfitsGrid = partitionIntoRows(latestProductsPage.getContent().stream().map(ProductTotalResponseDTO::getImgUrl).collect(Collectors.toList()), 2);
        return ResponseEntity.ok(outfitsGrid);
    }

    private List<List<String>> partitionIntoRows(List<String> list, int elementsPerRow) {
        List<List<String>> rows = new ArrayList<>();
        for (int i = 0; i < list.size(); i += elementsPerRow) {
            int end = Math.min(i + elementsPerRow, list.size());
            rows.add(list.subList(i, end));
        }
        return rows;
    }

    //한 브랜드에 어떤 상품들이 있는지 조회하는 화면 [최신순 조회] [0330]
    @GetMapping("/products/search/brand/latest")
    public ResponseEntity<?> getLatestProductsByBrand(@RequestParam(value = "brand") String brand, @RequestParam(value = "page")int page, @RequestParam(value = "size") int size){
        Pageable pageable = PageRequest.of(page,size);
        Page<ProductTotalResponseDTO> latestProductsPage = productService.getLatestProductsByBrand(brand, pageable);

        List<List<String>> outfitsGrid = partitionIntoRows(latestProductsPage.getContent().stream().map(ProductTotalResponseDTO::getImgUrl).collect(Collectors.toList()), 2);
        return ResponseEntity.ok(outfitsGrid);
    }




}
