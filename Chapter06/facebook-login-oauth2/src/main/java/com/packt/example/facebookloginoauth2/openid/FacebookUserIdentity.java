package com.packt.example.facebookloginoauth2.openid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class FacebookUserIdentity {

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserInfoService userInfoService;

    public FacebookUser findOrCreateFrom(OAuth2AccessToken accessToken) {

        Map<String, String> userInfo = userInfoService.getUserInfoFor(accessToken);
        Optional<FacebookUser> userAuth = repository.findByFacebookId(userInfo.get("id"));

        FacebookUser user = userAuth.orElseGet(() -> {
            FacebookLoginData loginData = new FacebookLoginData();
            loginData.setName(userInfo.get("name"));
            loginData.setId(userInfo.get("id"));
            loginData.setExpirationTime(accessToken.getExpiration().getTime());
            loginData.setToken(accessToken.getValue());
            return new FacebookUser(loginData);
        });

        return user;
    }

}
