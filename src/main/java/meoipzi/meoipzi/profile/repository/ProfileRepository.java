package meoipzi.meoipzi.profile.repository;


import meoipzi.meoipzi.profile.domain.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}