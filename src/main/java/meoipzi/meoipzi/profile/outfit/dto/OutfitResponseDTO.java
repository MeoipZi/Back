package meoipzi.meoipzi.profile.outfit.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import meoipzi.meoipzi.profile.outfit.domain.Outfit;
import meoipzi.meoipzi.product.dto.ProductListResponseDTO;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OutfitResponseDTO {
    //outfit id 에 맞는 3개의 상품 반환
    //코디
    private String imgUrl;
    private String content;
    private Long modelHeight;
    private Long modelWeight;
    private String modelInstagramId;

    //상품
    private List<ProductListResponseDTO> productListResponseDTOS;

    public OutfitResponseDTO(Outfit outfit){
        this.imgUrl = outfit.getImgUrl();
        this.content = outfit.getContent();
        this.modelHeight = outfit.getModelHeight();
        this.modelWeight = outfit.getModelWeight();
        this.modelInstagramId = outfit.getModelInstagramId();
    }

    public void setProducts(List<ProductListResponseDTO> productListResponseDTOS){
        this.productListResponseDTOS = productListResponseDTOS;
    }





}
