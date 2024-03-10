package meoipzi.meoipzi.heart.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HeartRequestDto {
    private String username;
    private Long contentId;
    private String contentType; //클라이언트 지정

    public void setUsername(String name) {
        this.username = name;
    }
    public void setContentId(Long id){
        this.contentId = id;
    }
}