package com.packt.example.clientclientcredentials.oauth;

import java.util.Calendar;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Repository;

@Repository
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SettingsRepository {

    private String accessToken;

    private Calendar expiresIn;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Calendar getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Calendar expiresIn) {
        this.expiresIn = expiresIn;
    }

}
