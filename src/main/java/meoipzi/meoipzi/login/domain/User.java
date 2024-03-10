package meoipzi.meoipzi.login.domain;

import lombok.*;
import jakarta.persistence.*;
import meoipzi.meoipzi.comment.domain.CommentCommunity;
import meoipzi.meoipzi.comment.domain.CommentOutfit;
import meoipzi.meoipzi.comment.domain.CommentShortForm;
import meoipzi.meoipzi.heart.domain.Heart;
import meoipzi.meoipzi.outfit.domain.Outfit;
import meoipzi.meoipzi.product.domain.Product;
import meoipzi.meoipzi.profile.domain.Profile;
import meoipzi.meoipzi.scrap.domain.Scrap;

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

    @Column(name = "nickname")
    private String nickname;

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

    @OneToMany(mappedBy = "user")
    private List<Heart> likes = new ArrayList<>();

   @OneToMany(mappedBy = "user")
    private List<Scrap> scraps = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @OneToMany(mappedBy = "user")
    private List<CommentCommunity> commentCommunities = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<CommentOutfit> commentOutfits  = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<CommentShortForm> commentShortForms = new ArrayList<>();
}
