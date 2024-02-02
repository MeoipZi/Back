package meoipzi.meoipzi.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import meoipzi.meoipzi.domain.Outfit;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class OutfitRequestDTO {
    private MultipartFile imgUrl;
    private String content; // 이게 굳이 필요한가?? -> 코디에 대한 제목 같은게 있으면 필요할 수 도???
    private Long modelHeight;
    private Long modelWeight;
    private String modelInstagramId;
    private String modelGender;

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
