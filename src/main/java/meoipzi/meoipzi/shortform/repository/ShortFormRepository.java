package meoipzi.meoipzi.shortform.repository;

import meoipzi.meoipzi.login.domain.User;
import meoipzi.meoipzi.shortform.domain.ShortForm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShortFormRepository extends JpaRepository<ShortForm, Long> {
    //Optional<Shortform> findByIdAndUserId(Long cmtId, Long userId);
    //List<Shortform> findAllOrderByCreated_atDesc(); // 최신순 조회
    //List<Shortform> findAllOrderByLikesCountDesc(); // 좋아요순 조회

    List<ShortForm> findTop3ByUserOrderByCreatedAtDesc(User user);
    List<ShortForm> findAllByOrderByCreatedAtDesc(); // 전체 숏폼을 최신순으로 정렬하여 가져옴
}