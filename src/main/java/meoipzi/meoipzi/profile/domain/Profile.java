package meoipzi.meoipzi.profile.domain;

import jakarta.persistence.*;
import lombok.*;
import meoipzi.meoipzi.login.domain.User;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "PROFILE")
@Getter @Setter
@NoArgsConstructor
public class Profile {
    @Id
    @Column(name = "profile_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long profileId;

    @JoinColumn(name = "user_id")
    @OneToOne(fetch = FetchType.LAZY)
    private User user; // 유저와 1:1 매핑(하나의 유저는 하나의 프로필 정보를 갖는다.)

    @Column(name = "nickname", length = 100)
    private String nickname; // 닉네임

    @Column(name = "imgUrl")
    private String imgUrl; // 프로필 이미지

    @Column(name = "height")
    private Integer height; // 키(신장)

    @Column(name = "height_secret")
    private boolean heightSecret; // 키 공개/비공개 여부

    @Column(name = "weight")
    private Integer weight; // 몸무게

    @Column(name = "weight_secret")
    private boolean weightSecret; // 몸무게 공개/비공개 여부


    @Builder
    public Profile(User user, String nickname, Integer height, boolean heightSecret, Integer weight, boolean weightSecret, String imgUrl) {
        this.user = user;
        this.nickname = nickname;
        this.height = height;
        this.heightSecret = heightSecret;
        this.weight = weight;
        this.weightSecret = weightSecret;
        this.imgUrl = imgUrl;
    }

    // 현재 로그인한 사용자의 username(이메일 주소) 반환
    public String getCurrentUsername(){
        return user != null ? user.getUsername() : null;
    }
}
