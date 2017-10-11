package com.packt.example.revokeserver.oauth.revoke;

public interface RevocationService {

    void revoke(String token);

    boolean supports(String tokenTypeHint);

}
