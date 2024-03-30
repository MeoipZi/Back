package meoipzi.meoipzi.homepage.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PartnersResponseDTO {
    @JsonIgnore
    private String imgUrl; // 제휴기업 로고 이미지
    private String shopUrl; // 해당 제휴기업의 쇼핑몰 링크
}
