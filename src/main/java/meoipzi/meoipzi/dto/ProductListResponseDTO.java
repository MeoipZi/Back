package meoipzi.meoipzi.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import meoipzi.meoipzi.domain.Product;

@Getter
@NoArgsConstructor
public class ProductListResponseDTO {
    private String imgUrl;
    private String title;
    private Long price;

    public ProductListResponseDTO(Product product){
        this.imgUrl =product.getImgUrl();
        this.title = product.getTitle();
        this.price = product.getPrice();
    }


}

