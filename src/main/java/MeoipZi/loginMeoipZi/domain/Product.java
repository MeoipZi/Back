package MeoipZi.loginMeoipZi.domain;

import jakarta.persistence.*;
import lombok.*;

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="product_id")
    private Long id;

    private String imgUrl;
    private String shopName;
    private String title;
    private Long price;
    private String shopUrl;


    @OneToMany(mappedBy = "product")
    private List<Scrap> scraps = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="outfit_id")// 연관관계 주인임 외래키임
    private Outfit outfit;

    @Builder
    public Product(String imgUrl, String shopName, String title, Long price, String shopUrl){
        this.imgUrl = imgUrl;
        this.shopName = shopName;
        this.title =title;
        this.price = price;
        this.shopUrl = shopUrl;
    }
}
