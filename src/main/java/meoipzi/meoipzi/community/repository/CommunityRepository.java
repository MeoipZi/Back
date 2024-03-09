package meoipzi.meoipzi.community.repository;

import meoipzi.meoipzi.community.domain.Community;
import meoipzi.meoipzi.login.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommunityRepository extends JpaRepository<Community, Long> {
    List<Community> findTop3ByUserOrderByCreatedAtDesc(User user);
    List<Community> findAllByOrderByCreatedAtDesc(); // 전체 커뮤니티를 최신순으로 정렬하여 가져옴
}
