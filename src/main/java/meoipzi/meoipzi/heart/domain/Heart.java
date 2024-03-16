package meoipzi.meoipzi.heart.domain;

import jakarta.persistence.*;
import lombok.*;
import meoipzi.meoipzi.community.domain.Community;
import meoipzi.meoipzi.login.domain.User;
import meoipzi.meoipzi.outfit.domain.Outfit;
import meoipzi.meoipzi.shortform.domain.ShortForm;

import java.time.LocalDateTime;

@Entity
@Table(name = "HEART")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Heart {

    @Id
    @Column(name="heart_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="community_id")
    private Community community;

    @ManyToOne
    @JoinColumn(name="shortform_id")
    private ShortForm shortForm;

    @ManyToOne
    @JoinColumn(name="outfit_id")
    private Outfit outfit;

    private String contentType;//outfit, shortform, community 중에 어디서 온 좋아요인지
    private LocalDateTime createdAt;
}