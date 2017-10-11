package com.packt.example.googleconnect.openid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserIdentity {

    @Autowired
    private UserRepository repository;

    public GoogleUser findOrCreateFrom(Claims claims) {

        Optional<GoogleUser> userAuth = repository.findByOpenIDSubject(claims.getSub());

        GoogleUser user = userAuth.orElseGet(() -> {
            OpenIDAuthentication openId = new OpenIDAuthentication();
            openId.setProvider(claims.getIss());
            openId.setSubject(claims.getSub());
            openId.setExpirationTime(claims.getExp());
            openId.setToken(claims.getAt_hash());

            return new GoogleUser(claims.getEmail(), openId);
        });

        if (!user.isCredentialsNonExpired()) {
            user.getOpenIDAuthentication()
                .setExpirationTime(claims.getExp());
        }

        return user;
    }

}
