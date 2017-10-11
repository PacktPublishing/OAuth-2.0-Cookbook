package com.packt.example.microsoftlogin.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private GoogleRegistrationProperties google;

    @Autowired
    private MicrosoftRegistrationProperties microsoft;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // more about logout issue https://github.com/spring-projects/spring-security/issues/4286
        http
            .authorizeRequests()
            .anyRequest().authenticated().and()
            .oauth2Login();
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(Arrays.asList(
            createGoogleRegistration(),
            createMicrosoftRegistration()));
    }

    private ClientRegistration createGoogleRegistration() {
        ClientRegistration registration = new ClientRegistration.Builder(google.getClientId())
            .authorizationUri(google.getAuthorizationUri())
            .clientSecret(google.getClientSecret())
            .tokenUri(google.getTokenUri())
            .redirectUri(google.getRedirectUri())
            .scope(google.getScopes().split(","))
            .clientName(google.getClientName())
            .clientAlias(google.getClientAlias())
            .jwkSetUri(google.getJwkSetUri())
            .authorizationGrantType(google.getAuthorizedGrantType())
            .userInfoUri(google.getUserInfoUri())
            .build();
        return registration;
    }

    private ClientRegistration createMicrosoftRegistration() {
        ClientRegistration registration = new ClientRegistration.Builder(microsoft.getClientId())
            .authorizationUri(microsoft.getAuthorizationUri())
            .clientSecret(microsoft.getClientSecret())
            .tokenUri(microsoft.getTokenUri())
            .redirectUri(microsoft.getRedirectUri())
            .scope(microsoft.getScopes().split(","))
            .clientName(microsoft.getClientName())
            .clientAlias(microsoft.getClientAlias())
            .jwkSetUri(microsoft.getJwkSetUri())
            .authorizationGrantType(microsoft.getAuthorizedGrantType())
            .userInfoUri(microsoft.getUserInfoUri())
            .clientAuthenticationMethod(ClientAuthenticationMethod.POST)
            .build();


        return registration;
    }

}
