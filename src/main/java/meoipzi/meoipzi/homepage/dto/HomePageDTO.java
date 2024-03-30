package meoipzi.meoipzi.homepage.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import meoipzi.meoipzi.outfit.dto.OutfitTotalResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class HomePageDTO {
    private List<VintageNewsResponseDTO> vintageNewsList;
    private List<PartnersResponseDTO> partnersList;
    private Page<OutfitTotalResponseDTO> styleList;
}
