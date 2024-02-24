package MeoipZi.loginMeoipZi.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "OUTFIT")
@NoArgsConstructor
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Outfit {

    @Id
    @Column(name="outfit_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imgUrl;
    private String content;
    private Long modelHeight;
    private Long modelWeight;
    private String modelInstagramId;
    private String modelGender;

    private int likesCount;


    @OneToMany(mappedBy = "outfit")
    private List<Heart> likes = new ArrayList<>();

    @OneToMany(mappedBy = "outfit")
    private List<CommentOutfit> commentOutfits = new ArrayList<>();

    @OneToMany(mappedBy = "outfit")
    private List<Product> products = new ArrayList<>();


    @Builder
    public Outfit( String imgUrl, String content,  Long modelHeight,  Long modelWeight,String modelInstagramId, String modelGender){
        this.imgUrl = imgUrl;
        this.content = content;
        this.modelHeight = modelHeight;
        this.modelWeight= modelWeight;
        this.modelInstagramId =modelInstagramId;
        this.modelGender = modelGender;
    }
}
