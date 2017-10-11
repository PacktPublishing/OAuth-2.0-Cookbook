package com.packt.example.facebooklogin.facebook;

import com.packt.example.facebooklogin.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.token.SecurityTokenRepository;
import org.springframework.security.oauth2.core.AccessToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

@Component
public class FacebookTokenStore implements SecurityTokenRepository<AccessToken> {

    @Autowired
    private FacebookAccountRepository repository;

    @Override
    public AccessToken loadSecurityToken(OAuth2AuthenticationToken authentication) {
        DefaultOAuth2User user = (DefaultOAuth2User) authentication.getPrincipal();
        String id = (String) user.getAttributes().get("id");

        Optional<FacebookAuth> facebookAuth = repository.findById(id);

        if (facebookAuth.isPresent()) {
            FacebookAuth auth = facebookAuth.get();
            return new AccessToken(AccessToken.TokenType.BEARER, id,
                Instant.ofEpochSecond(auth.getIssuedAt()),
                Instant.ofEpochSecond(auth.getExpirationTime()));
        }

        return null;
    }

    @Override
    public void saveSecurityToken(AccessToken securityToken, OAuth2AuthenticationToken authentication) {
        DefaultOAuth2User oAuth2User = (DefaultOAuth2User) authentication.getPrincipal();
        FacebookAuth facebookAuth = new FacebookAuth();
        facebookAuth.setUser(new User());
        facebookAuth.setId((String) oAuth2User.getAttributes().get("id"));
        facebookAuth.setExpirationTime(securityToken.getExpiresAt().getEpochSecond());
        facebookAuth.setIssuedAt(securityToken.getIssuedAt().getEpochSecond());

        repository.save(facebookAuth);
    }

    @Override
    public void removeSecurityToken(OAuth2AuthenticationToken authentication) {
        DefaultOAuth2User oAuth2User = (DefaultOAuth2User) authentication.getPrincipal();
        String id = (String) oAuth2User.getAttributes().get("id");
        Optional<FacebookAuth> facebookAuth = repository.findById(id);
        if (facebookAuth.isPresent()) {
            repository.delete(facebookAuth.get());
        }
    }
}
