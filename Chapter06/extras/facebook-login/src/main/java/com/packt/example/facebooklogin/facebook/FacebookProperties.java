package com.packt.example.facebooklogin.facebook;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "security.oauth2.client.facebook")
public class FacebookProperties {

    private String clientId;
    private String clientSecret;
    private String scopes;

    private String authorizationUri = "https://www.facebook.com/v2.10/dialog/oauth";
    private String redirectUri = "http://localhost:8080/oauth2/authorize/code/facebook";
    private String tokenUri = "https://graph.facebook.com/v2.10/oauth/access_token";
    private String clientName = "Facebook";
    private String clientAlias = "facebook";
    private AuthorizationGrantType authorizedGrantType = AuthorizationGrantType.AUTHORIZATION_CODE;
    private String userInfoUri = "https://graph.facebook.com/me";

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

    public String getAuthorizationUri() {
        return authorizationUri;
    }

    public void setAuthorizationUri(String authorizationUri) {
        this.authorizationUri = authorizationUri;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
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

    public void setAuthorizedGrantType(AuthorizationGrantType authorizedGrantType) {
        this.authorizedGrantType = authorizedGrantType;
    }

    public String getUserInfoUri() {
        return userInfoUri;
    }

    public void setUserInfoUri(String userInfoUri) {
        this.userInfoUri = userInfoUri;
    }
}
