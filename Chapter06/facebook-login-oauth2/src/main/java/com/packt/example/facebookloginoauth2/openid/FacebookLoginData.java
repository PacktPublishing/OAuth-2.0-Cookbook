package com.packt.example.facebookloginoauth2.openid;


import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;

@Entity
public class FacebookLoginData {

    @Id
    private String id;
    private long expirationTime;
    private long issuedAt;
    private String token;
    private String name;

    public boolean hasExpired() {
        OffsetDateTime expirationDateTime = OffsetDateTime.ofInstant(
                Instant.ofEpochSecond(expirationTime), ZoneId.systemDefault());

        OffsetDateTime now = OffsetDateTime.now(ZoneId.systemDefault());

        return now.isAfter(expirationDateTime);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(long expirationTime) {
        this.expirationTime = expirationTime;
    }

    public long getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(long issuedAt) {
        this.issuedAt = issuedAt;
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
