package meoipzi.meoipzi.outfit.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import jakarta.persistence.*;
import lombok.*;
import meoipzi.meoipzi.comment.domain.CommentOutfit;
import meoipzi.meoipzi.heart.domain.Heart;
import meoipzi.meoipzi.login.domain.User;
import meoipzi.meoipzi.product.domain.Product;


import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "outfits")
@NoArgsConstructor
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Outfit {
    @GeneratedValue
    @Id
    @Column(name="outfit_id")
    private Long id;

    private String imgUrl;
    private String content;
    private Long modelHeight;
    private Long modelWeight;
    private String modelInstagramId;
    private String modelGender;

    private int likesCount; //이거 추가
    private int commentCounts; //이거 추가

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="user_id")
    private User user;

    @OneToMany(mappedBy = "outfit")
    private List<Heart> likes = new ArrayList<>();

    @OneToMany(mappedBy = "outfit")
    private List<CommentOutfit> commentOutfits = new ArrayList<>();

    @OneToMany(mappedBy = "outfit")
    private List<Product> products = new ArrayList<>();






    @Builder
    public Outfit( String imgUrl, String content,  Long modelHeight,  Long modelWeight,String modelInstagramId, String modelGender, User user){
        this.imgUrl = imgUrl;
        this.content = content;
        this.modelHeight = modelHeight;
        this.modelWeight= modelWeight;
        this.modelInstagramId =modelInstagramId;
        this.modelGender = modelGender;
        this.user = user;
    }
}
