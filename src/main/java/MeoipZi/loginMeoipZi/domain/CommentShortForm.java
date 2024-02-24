package MeoipZi.loginMeoipZi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "CMTSF")
public class CommentShortForm {

    @Id
    @Column(name="cmtshortform_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
