package com.packt.example.clientimplicit.oauth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.resource.UserRedirectRequiredException;
import org.springframework.security.oauth2.client.token.AccessTokenProvider;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.implicit.ImplicitResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;

public class CustomImplicitAccessTokenProvider implements AccessTokenProvider {

    @Override
    public OAuth2AccessToken obtainAccessToken(
            OAuth2ProtectedResourceDetails details, AccessTokenRequest request)
            throws RuntimeException {

        ImplicitResourceDetails resource = (ImplicitResourceDetails) details;

        Map<String, String> requestParameters = getParametersForTokenRequest(
                resource, request);

        UserRedirectRequiredException redirectException = new UserRedirectRequiredException(
                resource.getUserAuthorizationUri(), requestParameters);

        throw redirectException;
    }

    @Override
    public boolean supportsResource(OAuth2ProtectedResourceDetails resource) {
        return resource instanceof ImplicitResourceDetails
                && "implicit".equals(resource.getGrantType());
    }

    @Override
    public OAuth2AccessToken refreshAccessToken(
            OAuth2ProtectedResourceDetails resource,
            OAuth2RefreshToken refreshToken, AccessTokenRequest request)
            throws UserRedirectRequiredException {
        return null;
    }

    @Override
    public boolean supportsRefresh(OAuth2ProtectedResourceDetails resource) {
        return false;
    }

    private Map<String, String> getParametersForTokenRequest(
            ImplicitResourceDetails resource, AccessTokenRequest request) {

        Map<String, String> queryString = new HashMap<String, String>();
        queryString.put("response_type", "token");
        queryString.put("client_id", resource.getClientId());

        if (resource.isScoped()) {
            queryString.put("scope",
                    resource.getScope().stream().reduce((a, b) -> a + " " + b)
                            .get());
        }

        String redirectUri = resource.getRedirectUri(request);
        if (redirectUri == null) {
            throw new IllegalStateException(
                    "No redirect URI available in request");
        }
        queryString.put("redirect_uri", redirectUri);

        return queryString;

    }

}
