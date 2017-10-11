package com.packt.example.googleconnect.profile;

import com.packt.example.googleconnect.openid.GoogleUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Optional<Profile> findByUser(GoogleUser user);
}
