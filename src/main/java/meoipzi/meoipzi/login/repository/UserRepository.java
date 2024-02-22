package meoipzi.meoipzi.login.repository;


import meoipzi.meoipzi.login.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(attributePaths = "authorities")//Lazy조회가 아닌 Eager조회로 authorities정보를 같이 가져옴
    Optional<User> findOneWithAuthoritiesByUsername(String username);
}