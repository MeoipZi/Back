package meoipzi.meoipzi.homepage.repository;

import meoipzi.meoipzi.community.domain.Community;
import meoipzi.meoipzi.homepage.domain.VintageNews;
import meoipzi.meoipzi.login.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VintageNewsRepository extends JpaRepository<VintageNews, Long> {
    List<VintageNews> findAllByOrderByCreatedAtDesc(); // 최신순 정렬된 VintageNews 리스트 반환
    // 굳이 Pageable 안 넣었음 => 필요 없을 것 같아서
}