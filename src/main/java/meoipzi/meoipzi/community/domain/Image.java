package meoipzi.meoipzi.community.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@Data
@Entity
@Getter @Setter
@Table(name = "IMAGE")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filePath; // 파일 경로(url)

    @ManyToOne
    @JoinColumn(name = "community_id")
    @JsonBackReference
    private Community community;

}