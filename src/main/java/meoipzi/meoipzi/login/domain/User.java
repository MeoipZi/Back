package meoipzi.meoipzi.login.domain;

import lombok.*;
import jakarta.persistence.*;
import meoipzi.meoipzi.outfit.domain.Outfit;
import meoipzi.meoipzi.product.domain.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "`USERS`")
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "email", length = 50, unique = true)
    private String username;

    //아직 oauth2를 안 넣어서 password 추가 - social이 없는 상태
    @Column(name = "password", length = 100)
    private String password;

    @Column(name = "activated")
    private boolean activated;

    @OneToMany(mappedBy = "user")
    private List<Outfit> outfitList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Product> productList = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<Authority> authorities;

// 이 브랜치의 domain에 heart, scrap, commentCommunity, commentoutfit, commentshortform을 안 넣어놔서 주석처리함.
//    @OneToMany(mappedBy = "user")
//    private List<Heart> likes = new ArrayList<>();
//
//    @OneToMany(mappedBy = "user")
//    private List<Scrap> scraps = new ArrayList<>();
//
//    @OneToMany(mappedBy = "user")
//    private List<CommentCommunity> commentCommunities = new ArrayList<>();
//
//    @OneToMany(mappedBy = "user")
//    private List<CommentOutfit> commentOutfits  = new ArrayList<>();
//
//    @OneToMany(mappedBy = "user")
//    private List<CommentShortForm> commentShortForms = new ArrayList<>();
}
