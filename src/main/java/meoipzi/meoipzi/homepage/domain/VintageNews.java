package meoipzi.meoipzi.homepage.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import meoipzi.meoipzi.common.BaseEntity;
import meoipzi.meoipzi.login.domain.User;

@Entity
@Getter
@Setter
@Table(name = "VINTAGE_NEWS")
@NoArgsConstructor
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class VintageNews extends BaseEntity {
    @GeneratedValue
    @Id
    @Column(name="vintageNews_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="user_id")
    private User user;

    private String imgUrl;

    @Builder
    public VintageNews(String imgUrl, User user){
        this.user = user;
        this.imgUrl = imgUrl;
    }
}
