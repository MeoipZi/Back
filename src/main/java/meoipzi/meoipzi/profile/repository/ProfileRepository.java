package meoipzi.meoipzi.profile.repository;


import meoipzi.meoipzi.login.domain.User;
import meoipzi.meoipzi.profile.domain.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Profile findByUser(User user);

}