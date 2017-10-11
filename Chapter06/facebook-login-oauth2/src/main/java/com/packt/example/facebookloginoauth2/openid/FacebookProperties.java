package com.packt.example.facebookloginoauth2.openid;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "facebook.config")
public class FacebookProperties {

    private String clientId;
    private String clientSecret;
    private String userInfoUri;
    private String appTokenUri;
    private String appAuthorizationUri;
    private String redirectUri;

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

    public String getUserInfoUri() {
        return userInfoUri;
    }

    public void setUserInfoUri(String userInfoUri) {
        this.userInfoUri = userInfoUri;
    }

    public String getAppTokenUri() {
        return appTokenUri;
    }

    public void setAppTokenUri(String appTokenUri) {
        this.appTokenUri = appTokenUri;
    }

    public String getAppAuthorizationUri() {
        return appAuthorizationUri;
    }

    public void setAppAuthorizationUri(String appAuthorizationUri) {
        this.appAuthorizationUri = appAuthorizationUri;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }
}
