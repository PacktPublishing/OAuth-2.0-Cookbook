package com.packt.example.popclient.oauth;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.ClientTokenServices;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;

@Service
public class OAuth2ClientTokenSevices implements ClientTokenServices {

    private ConcurrentHashMap<String, ClientUser> users = new ConcurrentHashMap<>();

    @Override
    public OAuth2AccessToken getAccessToken(OAuth2ProtectedResourceDetails resource, Authentication authentication) {
        ClientUser clientUser = getClientUser(authentication);

        if (clientUser.accessToken == null) return null;

        DefaultOAuth2AccessToken oAuth2AccessToken = new DefaultOAuth2AccessToken(clientUser.accessToken);
        oAuth2AccessToken.setAdditionalInformation(clientUser.additionalInformation);
        oAuth2AccessToken.setExpiration(new Date(clientUser.expirationTime));

        return oAuth2AccessToken;
    }

    @Override
    public void saveAccessToken(OAuth2ProtectedResourceDetails resource,
            Authentication authentication, OAuth2AccessToken accessToken) {
        ClientUser clientUser = getClientUser(authentication);

        clientUser.accessToken = accessToken.getValue();
        clientUser.expirationTime = accessToken.getExpiration().getTime();
        clientUser.additionalInformation = accessToken.getAdditionalInformation();

        users.put(clientUser.username, clientUser);
    }

    @Override
    public void removeAccessToken(OAuth2ProtectedResourceDetails resource,
            Authentication authentication) {
        users.remove(getClientUser(authentication).username);
    }

    private ClientUser getClientUser(Authentication authentication) {
        String username = ((User) authentication.getPrincipal()).getUsername();
        ClientUser clientUser = users.get(username);

        if (clientUser == null) {
            clientUser = new ClientUser(username);
        }

        return clientUser;
    }

    private static class ClientUser {
        private String username;
        private String accessToken;
        private Map<String, Object> additionalInformation;
        private long expirationTime;

        public ClientUser(String username) {
            this.username = username;
        }
    }
}
