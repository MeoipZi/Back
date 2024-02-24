package MeoipZi.loginMeoipZi.repository;

import MeoipZi.loginMeoipZi.domain.ShortForm;
import MeoipZi.loginMeoipZi.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShortFormRepository extends JpaRepository<ShortForm, Long> {

    List<ShortForm> findTop3ByUserOrderByCreatedAtDesc(User user);
    List<ShortForm> findAllByOrderByCreatedAtDesc(); // 전체 숏폼을 최신순으로 정렬하여 가져옴
}