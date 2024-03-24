package meoipzi.meoipzi.community.repository;

import meoipzi.meoipzi.community.domain.Community;
import meoipzi.meoipzi.login.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommunityRepository extends JpaRepository<Community, Long> {
    Page<Community> findAllByCategoryOrderByIdDesc(String category, Pageable pageable);
    Page<Community> findAllByCategoryOrderByLikesCount(String category, Pageable pageable);
    List<Community> findTop3ByUserOrderByCreatedAtDesc(User user);
    List<Community> findAllByUserOrderByCreatedAtDesc(User user);


}
