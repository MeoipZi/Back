package MeoipZi.repository;

import MeoipZi.domain.ShortForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShortformRepository extends JpaRepository<ShortForm, Long> {
    Page<ShortForm> findAllByOrderByIdDesc(Pageable pageable); // 최신순 조회
    Page<ShortForm> findAllByOrderByLikesCountDesc(Pageable pageable); // 좋아요순 조회
}
