package com.packt.example.facebookloginoauth2.openid;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository
    extends JpaRepository<FacebookUser, Long> {
    @Query("select u from FacebookUser u " +
            "inner join u.facebookLoginData o " +
            "where o.id = :facebookId")
    Optional<FacebookUser> findByFacebookId(
        @Param("facebookId") String facebookId);
}
