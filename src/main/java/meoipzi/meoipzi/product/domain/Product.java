package meoipzi.meoipzi.product.domain;

import jakarta.persistence.*;
import lombok.*;
import meoipzi.meoipzi.login.domain.User;
import meoipzi.meoipzi.outfit.domain.Outfit;


@Entity
@Getter
@Setter
@Table(name = "products")
@NoArgsConstructor
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {
    @GeneratedValue
    @Id
    @Column(name="product_id")
    private Long id;

    private String imgUrl;
    private String shopName;
    private String title; //content 같은 것임
    private Long price;
    private String shopUrl;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="user_id")
    private User user;

    //@OneToMany(mappedBy = "product")
    //private List<Scrap> scraps = new ArrayList<>();

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
