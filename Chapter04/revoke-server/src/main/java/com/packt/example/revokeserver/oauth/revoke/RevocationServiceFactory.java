package com.packt.example.revokeserver.oauth.revoke;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RevocationServiceFactory {

    @Autowired
    private List<RevocationService> revocationServices;

    public RevocationService create(String hint) {
        return revocationServices.stream()
            .filter(service -> service.supports(hint))
            .findFirst().orElse(noopRevocationService());
    }

    private RevocationService noopRevocationService() {
        return new RevocationService() {
            public boolean supports(String hint) { return false; }
            public void revoke(String token) { }
        };
    }
}
