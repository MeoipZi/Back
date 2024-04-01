package meoipzi.meoipzi.product.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import meoipzi.meoipzi.product.domain.Product;

@Getter
@Setter
@NoArgsConstructor
public class ProductTotalResponseDTO {
    private String imgUrl;
    private Long productId;
    public ProductTotalResponseDTO(Product product){
        this.imgUrl = product.getImgUrl();
        this.productId = product.getId();
    }
}
