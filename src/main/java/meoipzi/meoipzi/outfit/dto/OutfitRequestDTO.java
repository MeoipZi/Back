package meoipzi.meoipzi.outfit.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import meoipzi.meoipzi.outfit.domain.Outfit;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class OutfitRequestDTO {
    private Long userId;
    private MultipartFile imgUrl;
    private String content; // 피그마에 추가시키자! -> 코디에 대한 제목 같은게 있으면 필요할 수 도???
    private Long modelHeight;
    private Long modelWeight;
    private String modelInstagramId;
    private String modelGender; // 성별

    public Outfit toEntity(){
        return Outfit.builder()
                .imgUrl(imgUrl != null ? imgUrl.getOriginalFilename() : null)
                .content(content)
                .modelHeight(modelHeight)
                .modelWeight(modelWeight)
                .modelInstagramId(modelInstagramId)
                .modelGender(modelGender)
                .build();
    }
}
