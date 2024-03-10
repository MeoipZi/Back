package MeoipZi.repository;

import MeoipZi.domain.Community;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityRepository extends JpaRepository<Community, Long> {
    Page<Community> findAllByCategoryOrderByIdDesc(String category, Pageable pageable);
    Page<Community> findAllByCategoryOrderByLikesCount(String category, Pageable pageable);
}
