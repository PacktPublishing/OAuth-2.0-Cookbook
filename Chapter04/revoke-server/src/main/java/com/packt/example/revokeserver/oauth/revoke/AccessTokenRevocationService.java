package com.packt.example.revokeserver.oauth.revoke;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.stereotype.Service;

@Service
public class AccessTokenRevocationService implements RevocationService {

    @Autowired
    private ConsumerTokenServices tokenService;

    @Override
    public void revoke(String token) {
        tokenService.revokeToken(token);
    }

    @Override
    public boolean supports(String tokenTypeHint) {
        return "access_token".equals(tokenTypeHint);
    }

}
