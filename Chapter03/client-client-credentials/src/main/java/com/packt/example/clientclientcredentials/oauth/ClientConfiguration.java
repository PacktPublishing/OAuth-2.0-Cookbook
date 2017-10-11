package com.packt.example.clientclientcredentials.oauth;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenProviderChain;
import org.springframework.security.oauth2.client.token.ClientTokenServices;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@Configuration
@EnableOAuth2Client
public class ClientConfiguration {

    @Autowired
    private ClientTokenServices clientTokenServices;

    @Autowired
    private OAuth2ClientContext oauth2ClientContext;

    @Bean
    public OAuth2ProtectedResourceDetails passwordResourceDetails() {
        //@formatter:off
    ClientCredentialsResourceDetails details = new ClientCredentialsResourceDetails();

    details.setId("oauth2server");
    details.setTokenName("oauth_token");
    details.setClientId("clientadmin");
    details.setClientSecret("123");
    details.setAccessTokenUri("http://localhost:8080/oauth/token");
    details.setScope(Arrays.asList("admin"));

    details.setClientAuthenticationScheme(AuthenticationScheme.header);
    //@formatter:on

        return details;
    }

    @Bean
    public OAuth2RestTemplate oauth2RestTemplate() {

        OAuth2ProtectedResourceDetails resourceDetails = passwordResourceDetails();

        OAuth2RestTemplate template = new OAuth2RestTemplate(resourceDetails,
                oauth2ClientContext);

        AccessTokenProviderChain provider = new AccessTokenProviderChain(
                Arrays.asList(new ClientCredentialsAccessTokenProvider()));

        provider.setClientTokenServices(clientTokenServices);
        template.setAccessTokenProvider(provider);

        return template;
    }

}
