package MeoipZi.meoipzi.repository;

import MeoipZi.meoipzi.domain.Community;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface CommunityRepository extends JpaRepository<Community, Long> {
    Page<Community> findByCategory(String category, Pageable pageable);
    Page<Community> findAll(Pageable pageable);
}
