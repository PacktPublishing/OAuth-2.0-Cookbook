package com.packt.example.jweresource.oauth.jwt;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.util.JsonParser;
import org.springframework.security.oauth2.common.util.JsonParserFactory;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.LinkedHashMap;
import java.util.Map;

public class JweTokenEnhancer implements TokenEnhancer {
    public static final String TOKEN_ID = AccessTokenConverter.JTI;

    private JsonParser objectMapper = JsonParserFactory.create();
    private AccessTokenConverter tokenConverter;
    private JweTokenSerializer tokenSerializer;

    public JweTokenEnhancer(AccessTokenConverter tokenConverter,
        JweTokenSerializer tokenSerializer) {
        this.tokenConverter = tokenConverter;
        this.tokenSerializer = tokenSerializer;
    }

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        DefaultOAuth2AccessToken result = new DefaultOAuth2AccessToken(accessToken);
        Map<String, Object> info = new LinkedHashMap<>(accessToken.getAdditionalInformation());
        String tokenId = result.getValue();
        if (!info.containsKey(TOKEN_ID)) {
            info.put(TOKEN_ID, tokenId);
        }
        result.setAdditionalInformation(info);
        result.setValue(encode(result, authentication));

        return result;
    }

    private String encode(DefaultOAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        String content;
        try {
            content = objectMapper.formatMap(
                tokenConverter.convertAccessToken(accessToken, authentication));
            return tokenSerializer.encode(content);
        } catch (Exception e) {
            throw new IllegalStateException("Cannot convert access token to JSON", e);
        }

    }

}
