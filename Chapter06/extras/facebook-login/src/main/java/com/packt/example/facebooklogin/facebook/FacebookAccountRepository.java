package com.packt.example.facebooklogin.facebook;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FacebookAccountRepository extends JpaRepository<FacebookAuth, Long> {

    Optional<FacebookAuth> findById(String id);

}
