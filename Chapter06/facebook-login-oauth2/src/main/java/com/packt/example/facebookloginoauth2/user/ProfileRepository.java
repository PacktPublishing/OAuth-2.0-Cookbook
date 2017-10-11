package com.packt.example.facebookloginoauth2.user;

import com.packt.example.facebookloginoauth2.openid.FacebookUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Optional<Profile> findByUser(FacebookUser user);
}
