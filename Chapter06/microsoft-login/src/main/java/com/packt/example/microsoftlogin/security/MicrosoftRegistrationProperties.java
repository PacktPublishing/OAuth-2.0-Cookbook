package com.packt.example.microsoftlogin.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("security.oauth2.client.microsoft")
public class MicrosoftRegistrationProperties {

    private String clientId;
    private String clientSecret;
    private String scopes;

    private String redirectUri = "http://localhost:8080/oauth2/authorize/code/microsoft";
    private String authorizationUri = "https://login.microsoftonline.com/c11b2117-389d-41aa-868f-30ed6e72f416/oauth2/authorize";
    private String tokenUri = "https://login.microsoftonline.com/c11b2117-389d-41aa-868f-30ed6e72f416/oauth2/token";
    private String clientName = "Microsoft";
    private String clientAlias = "microsoft";
    private AuthorizationGrantType authorizedGrantType = AuthorizationGrantType.AUTHORIZATION_CODE;
    private String userInfoUri = "https://login.microsoftonline.com/c11b2117-389d-41aa-868f-30ed6e72f416/openid/userinfo";
    private String userInfoNameAttributeKey = "name";
    private String jwkSetUri = "https://login.microsoftonline.com/common/discovery/keys";

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
