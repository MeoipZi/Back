package meoipzi.meoipzi.outfit.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import meoipzi.meoipzi.outfit.domain.Outfit;

@Getter
@Setter
@NoArgsConstructor
public class OutfitTotalResponseDTO {
    //검색하기 창은 ...client
    //private Long id;
    private String imgUrl;

    public OutfitTotalResponseDTO(Outfit outfit) {
        //this.id = outfit.getId();
        this.imgUrl = outfit.getImgUrl(); // Outfit 엔티티의 getter 메서드에 맞게 수정하세요.
    }


}
