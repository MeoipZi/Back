package MeoipZi.loginMeoipZi.domain;

import lombok.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "AUTHORITY")
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Authority {//ENUM으로 바꿀까...

    @Id
    @Column(name = "authority_name", length = 50)
    private String authorityName;
}
