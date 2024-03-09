package meoipzi.meoipzi.scrap.domain;

import jakarta.persistence.*;
import lombok.*;
import meoipzi.meoipzi.login.domain.User;
import meoipzi.meoipzi.outfit.domain.Outfit;
import meoipzi.meoipzi.product.domain.Product;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Builder
@Table(name = "SCRAP")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Scrap {

    @GeneratedValue(strategy = GenerationType.IDENTITY) @Id @Column(name="scrap_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="outfit_id")
    private Outfit outfit;

    private String contentType;// product or outfit
    private LocalDateTime createdAt;

}