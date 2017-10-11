package com.packt.example.openidspring5.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("security.oauth2.client.google")
public class GoogleRegistrationProperties {

    private String clientId;
    private String clientSecret;
    private String scopes;
    private String redirectUri;
    private String authorizationUri;
    private String tokenUri;
    private String clientName = "Google";
    private String clientAlias = "google";
    private String userInfoUri;
    private String userInfoNameAttributeKey = "name";
    private String jwkSetUri;

    private AuthorizationGrantType authorizedGrantType
            = AuthorizationGrantType.AUTHORIZATION_CODE;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getScopes() {
        return scopes;
    }

    public void setScopes(String scopes) {
        this.scopes = scopes;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public String getAuthorizationUri() {
        return authorizationUri;
    }

    public void setAuthorizationUri(String authorizationUri) {
        this.authorizationUri = authorizationUri;
    }

    public String getTokenUri() {
        return tokenUri;
    }

    public void setTokenUri(String tokenUri) {
        this.tokenUri = tokenUri;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientAlias() {
        return clientAlias;
    }

    public void setClientAlias(String clientAlias) {
        this.clientAlias = clientAlias;
    }

    public AuthorizationGrantType getAuthorizedGrantType() {
        return authorizedGrantType;
    }

    public String getUserInfoUri() {
        return userInfoUri;
    }

    public void setUserInfoUri(String userInfoUri) {
        this.userInfoUri = userInfoUri;
    }

    public String getUserInfoNameAttributeKey() {
        return userInfoNameAttributeKey;
    }

    public void setUserInfoNameAttributeKey(String userInfoNameAttributeKey) {
        this.userInfoNameAttributeKey = userInfoNameAttributeKey;
    }

    public String getJwkSetUri() {
        return jwkSetUri;
    }

    public void setJwkSetUri(String jwkSetUri) {
        this.jwkSetUri = jwkSetUri;
    }
}
