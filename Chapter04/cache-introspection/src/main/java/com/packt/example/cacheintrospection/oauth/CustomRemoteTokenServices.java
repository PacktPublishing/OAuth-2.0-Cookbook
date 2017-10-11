package com.packt.example.cacheintrospection.oauth;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

public class CustomRemoteTokenServices extends RemoteTokenServices {

    private RemoteTokenServices remoteTokenServices;

    public CustomRemoteTokenServices(RemoteTokenServices remoteTokenServices) {
        this.remoteTokenServices = remoteTokenServices;
    }

    @Override
    @Cacheable("oauth2")
    public OAuth2Authentication loadAuthentication(String accessToken) throws AuthenticationException,
            InvalidTokenException {
        return remoteTokenServices.loadAuthentication(accessToken);
    }

    @Override
    public OAuth2AccessToken readAccessToken(String accessToken) {
        return remoteTokenServices.readAccessToken(accessToken);
    }

}
