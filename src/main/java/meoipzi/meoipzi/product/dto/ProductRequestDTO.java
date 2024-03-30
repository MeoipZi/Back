package meoipzi.meoipzi.product.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import meoipzi.meoipzi.login.domain.User;
import meoipzi.meoipzi.product.domain.Product;
import org.springframework.web.multipart.MultipartFile;
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductRequestDTO {
    private String username;
    private Long outfitId;
    @JsonIgnore
    private MultipartFile imgUrl;
    private String shopName;
    private String title;
    private Long price;
    private String shopUrl;
    private String category;
    private String brand;




    public Product toEntity(User user){
        return Product.builder()
                .imgUrl(imgUrl != null ? imgUrl.getOriginalFilename() : null)
                .shopName(shopName)
                .title(title)
                .price(price)
                .shopUrl(shopUrl)
                .category(category)
                .brand(brand)
                .user(user)
                .build();
    }
}
