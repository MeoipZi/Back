package MeoipZi.repository.CommentRepository;

import MeoipZi.domain.CommentShortForm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentShortformRepository extends JpaRepository<CommentShortForm, Long> {
    List<CommentShortForm> findByShortFormId(Long shortFormId);
}
