package meoipzi.meoipzi.profile.outfit.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class OutfitUpdateRequestDTO {
    private String username;
    @JsonIgnore
    private MultipartFile imgUrl;
    private String content; // 피그마에 추가시키자! -> 코디에 대한 제목 같은게 있으면 필요할 수 도???
    //private Long modelHeight;
    //private Long modelWeight;
    //private String modelInstagramId;
    //private String modelGender; // 성별

}
