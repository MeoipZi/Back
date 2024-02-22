/*package meoipzi.meoipzi.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
*//*
@Entity
@Getter@Setter
@Table(name = "scraps")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Scrap {
    @GeneratedValue @Id @Column(name="scrap_id")
    private Long id;
    private LocalDateTime createDateTime;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="product_id")
    private Product product;
}
*/