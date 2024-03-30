package meoipzi.meoipzi.homepage.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import meoipzi.meoipzi.homepage.domain.VintageNews;
import meoipzi.meoipzi.product.domain.Product;

@Getter
@Setter
@NoArgsConstructor
public class VintageNewsResponseDTO {
    private String imgUrl; // 빈티지 소식

    public VintageNewsResponseDTO(VintageNews vintageNews){
        this.imgUrl =vintageNews.getImgUrl();
    }
}
