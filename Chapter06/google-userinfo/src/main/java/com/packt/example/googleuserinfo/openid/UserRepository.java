package com.packt.example.googleuserinfo.openid;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<GoogleUser, Long> {
    @Query("select u from GoogleUser u " +
            "inner join u.openIDAuthentication o " +
            "where o.subject = :subject")
    Optional<GoogleUser> findByOpenIDSubject(
            @Param("subject") String subject);
}
