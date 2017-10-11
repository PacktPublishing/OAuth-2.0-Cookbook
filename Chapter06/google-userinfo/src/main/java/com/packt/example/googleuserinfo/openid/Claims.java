package com.packt.example.googleuserinfo.openid;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import java.io.IOException;

public class Claims {

    private String iss;
    private String sub;
    private String at_hash;
    private String email;
    private Long exp;

    public static Claims createFrom(ObjectMapper jsonMapper, OAuth2AccessToken accessToken) {
        try {
            String idToken = accessToken.getAdditionalInformation().get("id_token").toString();
            Jwt decodedToken = JwtHelper.decode(idToken);
            return jsonMapper.readValue(decodedToken.getClaims(), Claims.class);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getIss() {
        return iss;
    }

    public String getSub() {
        return sub;
    }

    public String getAt_hash() {
        return at_hash;
    }

    public String getEmail() {
        return email;
    }

    public Long getExp() {
        return exp;
    }
}
