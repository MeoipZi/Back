package meoipzi.meoipzi.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long id;
    //String email;
    @NotEmpty
    private String profileNickname;
    @NotEmpty
    private String profileImage;

    @OneToMany(mappedBy = "user")
    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Scrap> scraps = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<CommentCommunity> commentCommunities = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<CommentOutfit> commentOutfits  = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<CommentShortForm> commentShortForms = new ArrayList<>();
}
