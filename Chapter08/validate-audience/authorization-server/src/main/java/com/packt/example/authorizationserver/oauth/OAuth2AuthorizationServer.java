package com.packt.example.authorizationserver.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

@Configuration @EnableAuthorizationServer
public class OAuth2AuthorizationServer extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.checkTokenAccess("hasAuthority('introspection')");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // configures two clients: client-a and client-b
        // tokens generated for client-a, will be valid just for resource-a
        // tokens generated for client-b, will be valid just for resource-b
        clients.inMemory()
            .withClient("client-a").secret("123")
            .authorizedGrantTypes("password")
            .scopes("read_profile", "read_contacts")
            .authorities("introspection")
            .resourceIds("resource-a")
        .and()
            .withClient("client-b").secret("123")
            .authorizedGrantTypes("password")
            .scopes("read_profile")
            .authorities("introspection")
            .resourceIds("resource-b");
    }
}
