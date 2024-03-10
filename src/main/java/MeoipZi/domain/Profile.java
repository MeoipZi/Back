package MeoipZi.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "`PROFILE`")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Profile {
    @Id
    @Column(name = "profile")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long profileId;

    @OneToOne(mappedBy = "profile", fetch = FetchType.LAZY)
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
    public Profile(String nickname, String imgUrl, Integer height,
                   boolean heightSecret, Integer weight, boolean weightSecret){
        this.nickname = nickname;
        this.imgUrl = imgUrl;
        this.height = height;
        this.weight = weight;
        this.heightSecret = heightSecret;
        this.weightSecret = weightSecret;
    }

    // profile 엔티티 수정 메서드: profileId 리턴
    public Long update(MultipartFile imgUrl, String nickname, Integer height, Integer weight,
                       boolean heightSecret, boolean weightSecret) {
        this.nickname = nickname;
        this.height = height;
        this.weight = weight;
        this.heightSecret = heightSecret;
        this.weightSecret = weightSecret;
        this.imgUrl = (imgUrl != null)? imgUrl.getOriginalFilename() : null;
        return this.profileId;
    }

}
