package com.packt.example.facebookloginoauth2.openid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenProviderChain;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

import java.util.Arrays;

@Configuration
@EnableOAuth2Client
public class FacebookConfiguration {

    @Autowired
    private FacebookProperties properties;

    @Bean
    public OAuth2ProtectedResourceDetails resourceDetails() {
        AuthorizationCodeResourceDetails details = new AuthorizationCodeResourceDetails();
        details.setClientId(properties.getClientId());
        details.setClientSecret(properties.getClientSecret());
        details.setUserAuthorizationUri(properties.getAppAuthorizationUri());
        details.setAccessTokenUri(properties.getAppTokenUri());
        details.setPreEstablishedRedirectUri(properties.getRedirectUri());
        details.setScope(Arrays.asList("email", "public_profile"));
        details.setClientAuthenticationScheme(AuthenticationScheme.query);
        details.setUseCurrentUri(false);
        return details;
    }

    @Bean
    public OAuth2RestTemplate restTemplate(OAuth2ClientContext context) {
        OAuth2RestTemplate rest = new OAuth2RestTemplate(resourceDetails(), context);
        rest.setAccessTokenProvider(
            new AccessTokenProviderChain(
                Arrays.asList(new AuthorizationCodeAccessTokenProvider())));
        return rest;
    }

}
