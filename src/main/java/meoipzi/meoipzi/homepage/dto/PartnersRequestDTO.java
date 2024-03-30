package meoipzi.meoipzi.homepage.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import meoipzi.meoipzi.homepage.domain.Partners;
import meoipzi.meoipzi.homepage.domain.VintageNews;
import meoipzi.meoipzi.login.domain.User;
import org.springframework.web.multipart.MultipartFile;
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PartnersRequestDTO {
    private String username;
    private String shopName;
    @JsonIgnore
    private MultipartFile imgUrl;
    private String shopUrl;

    public Partners toEntity(User user){
        return Partners.builder()
                .imgUrl(imgUrl != null ? imgUrl.getOriginalFilename() : null)
                .shopName(shopName)
                .shopUrl(shopUrl)
                .user(user)
                .build();
    }
}

