package meoipzi.meoipzi.product.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import meoipzi.meoipzi.product.domain.Product;
import org.springframework.web.multipart.MultipartFile;
@Getter
@Setter
@NoArgsConstructor
public class ProductRequestDTO {
    private Long userId;
    private Long outfitId;

    private MultipartFile imgUrl;
    private String shopName;
    private String title;
    private Long price;
    private String shopUrl;

    public Product toEntity(){
        return Product.builder()
                .imgUrl(imgUrl != null ? imgUrl.getOriginalFilename() : null)
                .shopName(shopName)
                .title(title)
                .price(price)
                .shopUrl(shopUrl)
                .build();
    }
}
