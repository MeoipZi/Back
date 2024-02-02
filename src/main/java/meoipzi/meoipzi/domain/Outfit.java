package meoipzi.meoipzi.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "outfits")
@NoArgsConstructor
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
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



    @OneToMany(mappedBy = "outfit")
    private List<Like> likes = new ArrayList<>();

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
