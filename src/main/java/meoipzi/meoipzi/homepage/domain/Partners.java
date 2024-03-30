package meoipzi.meoipzi.homepage.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import meoipzi.meoipzi.login.domain.User;

@Entity
@Getter
@Setter
@Table(name = "PARTNERS")
@NoArgsConstructor
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Partners {
    @GeneratedValue
    @Id
    @Column(name="vintageNews_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="user_id")
    private User user;

    private String imgUrl;
    private String shopName; // 제휴 기업 이름
    private String shopUrl; // 제휴 기업 연결링크

    @Builder
    public Partners(User user, String imgUrl, String shopName, String shopUrl){
        this.user = user;
        this.imgUrl = imgUrl;
        this.shopName = shopName;
        this.shopUrl = shopUrl;
    }
}
