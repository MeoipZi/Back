package meoipzi.meoipzi.homepage.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import meoipzi.meoipzi.homepage.domain.Partners;

@Getter
@Setter
@NoArgsConstructor
public class PartnersDetailResponseDTO {
    public String shopUrl;
    public PartnersDetailResponseDTO(Partners partners){
        this.shopUrl = partners.getShopUrl();
    }
}

