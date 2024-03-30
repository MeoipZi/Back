package meoipzi.meoipzi.product.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import meoipzi.meoipzi.product.domain.Product;

@Getter
@NoArgsConstructor
public class ProductListResponseDTO {
    private String imgUrl;
    private String title;
    private Long price;
    private Long productId;

    public ProductListResponseDTO(Product product){
        this.imgUrl =product.getImgUrl();
        this.title = product.getTitle();
        this.price = product.getPrice();
        this.productId = product.getId();
    }


}

