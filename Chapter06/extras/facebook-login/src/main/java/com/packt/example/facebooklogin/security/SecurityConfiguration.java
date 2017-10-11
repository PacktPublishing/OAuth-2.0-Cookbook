package com.packt.example.facebooklogin.security;

import com.packt.example.facebooklogin.facebook.FacebookAuthorizationGrantTokenExchanger;
import com.packt.example.facebooklogin.facebook.FacebookProperties;
import com.packt.example.facebooklogin.facebook.FacebookTokenStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

import java.net.URI;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private FacebookProperties properties;

    @Autowired
    private FacebookTokenStore tokenStore;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers("/").permitAll().and()
            .authorizeRequests()
            .antMatchers("/profile/*").authenticated().and()
            .authorizeRequests().anyRequest().authenticated().and()
            .httpBasic().and()
            .oauth2Login().and()
            .csrf().disable();

        // custom token exchanger for Facebook
        http.oauth2Login()
            .userInfoEndpoint().userNameAttributeName("name", URI.create(properties.getUserInfoUri())).and()
            .tokenEndpoint().accessTokenRepository(tokenStore)
            .authorizationCodeTokenExchanger(
                new FacebookAuthorizationGrantTokenExchanger());
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        ClientRegistration registration = new ClientRegistration.Builder(properties.getClientId())
                .authorizationUri(properties.getAuthorizationUri())
                .clientSecret(properties.getClientSecret())
                .tokenUri(properties.getTokenUri())
                .redirectUri(properties.getRedirectUri())
                .scope(properties.getScopes().split(","))
                .clientName(properties.getClientName())
                .clientAlias(properties.getClientAlias())
                .authorizationGrantType(properties.getAuthorizedGrantType())
                .userInfoUri(properties.getUserInfoUri())
                .clientAuthenticationMethod(new ClientAuthenticationMethod("get"))
                .build();

        return new InMemoryClientRegistrationRepository(Arrays.asList(registration));
    }


}
