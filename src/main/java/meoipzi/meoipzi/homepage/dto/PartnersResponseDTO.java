package meoipzi.meoipzi.homepage.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import meoipzi.meoipzi.homepage.domain.Partners;

@Getter
@Setter
@RequiredArgsConstructor
public class PartnersResponseDTO {
    private Long partnersId;
    private String imgUrl; // 제휴기업 로고 이미지
    private String shopName; // 제휴기업
    private String shopUrl; // 해당 제휴기업의 쇼핑몰 링크
    public PartnersResponseDTO(Partners partners){
        this.partnersId = partners.getId();
        this.shopName = partners.getShopName();
        this.imgUrl = partners.getImgUrl();
        this.shopUrl = partners.getShopUrl();
    }
}
