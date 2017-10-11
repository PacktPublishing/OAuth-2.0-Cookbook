package com.packt.example.googleuserinfo.openid;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;

@Entity
public class OpenIDAuthentication {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subject;

    private String provider;

    private long expirationTime;

    private String token;

    // new attribute (retrieved from user info endpoint)
    private String name;

    public boolean hasExpired() {
        OffsetDateTime expirationDateTime = OffsetDateTime.ofInstant(
                Instant.ofEpochSecond(expirationTime), ZoneId.systemDefault());

        OffsetDateTime now = OffsetDateTime.now(ZoneId.systemDefault());

        return now.isAfter(expirationDateTime);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public long getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(long expirationTime) {
        this.expirationTime = expirationTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
