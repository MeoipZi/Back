package meoipzi.meoipzi.shortform.repository;

import meoipzi.meoipzi.login.domain.User;
import meoipzi.meoipzi.shortform.domain.ShortForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShortFormRepository extends JpaRepository<ShortForm, Long> {
    Page<ShortForm> findAllByOrderByIdDesc(Pageable pageable); // 최신순 조회
    Page<ShortForm> findAllByOrderByLikesCountDesc(Pageable pageable); // 좋아요순 조회
    List<ShortForm> findTop3ByUserOrderByCreatedAtDesc(User user);
    List<ShortForm> findAllByUserOrderByCreatedAtDesc(User user);


}