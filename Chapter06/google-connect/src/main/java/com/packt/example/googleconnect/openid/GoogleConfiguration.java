package com.packt.example.googleconnect.openid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenProviderChain;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

import java.util.Arrays;

@Configuration
@EnableOAuth2Client
public class GoogleConfiguration {

    @Autowired
    private GoogleProperties properties;

    @Bean
    public OAuth2ProtectedResourceDetails resourceDetails() {
        AuthorizationCodeResourceDetails details = new AuthorizationCodeResourceDetails();
        details.setClientId(properties.getClientId());
        details.setClientSecret(properties.getClientSecret());

        // URLs retrieved from https://accounts.google.com/.well-known/openid-configuration
        details.setUserAuthorizationUri("https://accounts.google.com/o/oauth2/v2/auth");
        details.setAccessTokenUri("https://www.googleapis.com/oauth2/v4/token");
        details.setPreEstablishedRedirectUri("http://localhost:8080/google/callback");
        details.setScope(Arrays.asList("openid", "email", "profile"));
        details.setUseCurrentUri(false);
        return details;
    }

    @Bean
    public OAuth2RestTemplate restTemplate(OAuth2ClientContext context) {
        OAuth2RestTemplate rest = new OAuth2RestTemplate(resourceDetails(), context);
        AccessTokenProviderChain providerChain = new AccessTokenProviderChain(
                Arrays.asList(new AuthorizationCodeAccessTokenProvider()));
        rest.setAccessTokenProvider(providerChain);
        return rest;
    }

}
