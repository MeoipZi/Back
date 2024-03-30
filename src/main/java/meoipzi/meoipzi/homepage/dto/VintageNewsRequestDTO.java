package meoipzi.meoipzi.homepage.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import meoipzi.meoipzi.homepage.domain.VintageNews;
import meoipzi.meoipzi.login.domain.User;
import org.springframework.web.multipart.MultipartFile;
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VintageNewsRequestDTO {
    private String username;
    @JsonIgnore
    private MultipartFile imgUrl;
    public VintageNews toEntity(User user){
        return VintageNews.builder()
                .imgUrl(imgUrl != null ? imgUrl.getOriginalFilename() : null)
                .user(user)
                .build();
    }


}