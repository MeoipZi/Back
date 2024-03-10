package meoipzi.meoipzi.mypage.dto;

import lombok.*;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyScrapsDto {

    private List<MyImageResponseDto> scrapedOutfits;
    private List<MyImageResponseDto> scrapedProducts;

}