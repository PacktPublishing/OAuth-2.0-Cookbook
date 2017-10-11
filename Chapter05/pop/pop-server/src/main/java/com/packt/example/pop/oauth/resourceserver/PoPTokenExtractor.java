package com.packt.example.pop.oauth.resourceserver;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.TokenExtractor;

import javax.servlet.http.HttpServletRequest;

public class PoPTokenExtractor implements TokenExtractor {

    private TokenExtractor delegate;

    public PoPTokenExtractor(TokenExtractor delegate) {
        this.delegate = delegate;
    }

    @Override
    public Authentication extract(HttpServletRequest request) {
        Authentication authentication = delegate.extract(request);

        if (authentication != null) {
            PoPAuthenticationToken popAuthenticationToken = new PoPAuthenticationToken(authentication);
            popAuthenticationToken.setNonce(request.getHeader("nonce"));
            return popAuthenticationToken;
        }

        return authentication;
    }
}
