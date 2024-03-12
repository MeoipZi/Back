package meoipzi.meoipzi.product.domain;

import jakarta.persistence.*;
import lombok.*;
import meoipzi.meoipzi.login.domain.User;
import meoipzi.meoipzi.profile.outfit.domain.Outfit;
import meoipzi.meoipzi.scrap.domain.Scrap;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@Table(name = "PRODUCT")
@NoArgsConstructor
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {
    @GeneratedValue
    @Id
    @Column(name="product_id")
    private Long id;

    private String imgUrl;
    private String shopUrl;
    private String shopName;
    private String title; //content 같은 것임
    private Long price;
    //이거 추가함 0312
    private String category;
    private String brand;

    //시간 추가하기
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="user_id")
    private User user;

    @OneToMany(mappedBy = "product")
    private List<Scrap> scraps = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="outfit_id")// 연관관계 주인임 외래키임
    private Outfit outfit;

    @Builder
    public Product(String imgUrl, String shopName, String title, Long price, String shopUrl, User user){
        this.imgUrl = imgUrl;
        this.shopName = shopName;
        this.title =title;
        this.price = price;
        this.shopUrl = shopUrl;
        this.user = user;
    }
}
