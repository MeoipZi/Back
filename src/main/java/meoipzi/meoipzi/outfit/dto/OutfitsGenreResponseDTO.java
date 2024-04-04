package meoipzi.meoipzi.outfit.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import meoipzi.meoipzi.outfit.domain.Outfit;
import meoipzi.meoipzi.product.domain.Product;

@Getter
@Setter
@NoArgsConstructor
public class OutfitsGenreResponseDTO {
    private String imgUrl;
    private Long outfitId;
    public OutfitsGenreResponseDTO(Outfit outfit){
        this.imgUrl = outfit.getImgUrl();
        this.outfitId = outfit.getId();
    }
}
