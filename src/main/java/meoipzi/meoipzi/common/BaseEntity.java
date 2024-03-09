package meoipzi.meoipzi.common;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Duration;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)

public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @CreatedDate
    public LocalDateTime createdAt;

    @LastModifiedDate
    public LocalDateTime updatedAt;

    public String formattedCreatedAt = calculateTimeAgo(createdAt);

    // 문자열 계산 메서드
    private String calculateTimeAgo(LocalDateTime createdAt){
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(createdAt ,now);

        long minutes = duration.toMinutes();
        long hours = duration.toHours();
        long days = duration.toDays();
        long weeks = days / 7;
        long months = days / 30;

        if(minutes < 1) {
            return "방금 전";
        } else if(hours < 1) {
            return minutes + "분 전";
        } else if (days < 1) {
            return hours + "시간 전";
        } else if(days < 7) {
            return days + "일 전";
        } else if (weeks < 4) {
            return weeks + "주 전";
        } else {
            return months + "개월 전";
        }
    }
}