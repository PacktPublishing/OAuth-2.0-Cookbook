package com.packt.example.facebooklogin.facebook;

import com.nimbusds.oauth2.sdk.AccessTokenResponse;
import com.nimbusds.oauth2.sdk.AuthorizationCode;
import com.nimbusds.oauth2.sdk.AuthorizationCodeGrant;
import com.nimbusds.oauth2.sdk.AuthorizationGrant;
import com.nimbusds.oauth2.sdk.ParseException;
import com.nimbusds.oauth2.sdk.SerializeException;
import com.nimbusds.oauth2.sdk.TokenResponse;
import com.nimbusds.oauth2.sdk.auth.ClientAuthentication;
import com.nimbusds.oauth2.sdk.auth.Secret;
import com.nimbusds.oauth2.sdk.http.CommonContentTypes;
import com.nimbusds.oauth2.sdk.http.HTTPRequest;
import com.nimbusds.oauth2.sdk.id.ClientID;
import com.nimbusds.oauth2.sdk.util.URLUtils;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.oauth2.client.authentication.AuthorizationCodeAuthenticationToken;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationException;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.web.AuthorizationGrantTokenExchanger;
import org.springframework.security.oauth2.core.AccessToken;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.endpoint.TokenResponseAttributes;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class FacebookAuthorizationGrantTokenExchanger implements AuthorizationGrantTokenExchanger<AuthorizationCodeAuthenticationToken> {

    @Override
    public TokenResponseAttributes exchange(
        AuthorizationCodeAuthenticationToken authorizationCodeAuthenticationToken)
        throws OAuth2AuthenticationException {

        ClientRegistration clientRegistration = authorizationCodeAuthenticationToken.getClientRegistration();

        AuthorizationCode authorizationCode = new AuthorizationCode(
            authorizationCodeAuthenticationToken.getAuthorizationCode());
        AuthorizationGrant authorizationCodeGrant = new AuthorizationCodeGrant(
            authorizationCode, URI.create(clientRegistration.getRedirectUri()));
        URI tokenUri = URI.create(clientRegistration.getProviderDetails().getTokenUri());

        ClientID clientId = new ClientID(clientRegistration.getClientId());
        Secret clientSecret = new Secret(clientRegistration.getClientSecret());
        ClientAuthentication clientAuthentication = new ClientSecretGet(clientId, clientSecret);

        try {
            HTTPRequest httpRequest = createTokenRequest(
                    clientRegistration, authorizationCodeGrant,
                    tokenUri, clientAuthentication);

            TokenResponse tokenResponse = TokenResponse.parse(httpRequest.send());

            if (!tokenResponse.indicatesSuccess()) {
                OAuth2Error errorObject = new OAuth2Error("invalid_token_response");
                throw new OAuth2AuthenticationException(errorObject, "error");
            }

            return createTokenResponse((AccessTokenResponse) tokenResponse);

        } catch (MalformedURLException e) {
            throw new SerializeException(e.getMessage(), e);
        } catch (ParseException pe) {
            throw new OAuth2AuthenticationException(new OAuth2Error("invalid_token_response"), pe);
        } catch (IOException ioe) {
            throw new AuthenticationServiceException(
                "An error occurred while sending the Access Token Request: " +
                ioe.getMessage(), ioe);
        }

    }

    private TokenResponseAttributes createTokenResponse(AccessTokenResponse tokenResponse) {
        AccessTokenResponse accessTokenResponse = tokenResponse;
        String accessToken = accessTokenResponse.getTokens().getAccessToken().getValue();
        AccessToken.TokenType accessTokenType = AccessToken.TokenType.BEARER;
        long expiresIn = accessTokenResponse.getTokens().getAccessToken().getLifetime();
        Set<String> scopes = Collections.emptySet();
        if (!CollectionUtils.isEmpty(accessTokenResponse.getTokens().getAccessToken().getScope())) {
            scopes = new HashSet<>(accessTokenResponse.getTokens().getAccessToken().getScope().toStringList());
        }

        Map<String, Object> additionalParameters = accessTokenResponse.getCustomParameters().entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return TokenResponseAttributes.withToken(accessToken)
                .tokenType(accessTokenType)
                .expiresIn(expiresIn)
                .scopes(scopes)
                .additionalParameters(additionalParameters)
                .build();
    }

    private HTTPRequest createTokenRequest(ClientRegistration clientRegistration,
           AuthorizationGrant authorizationCodeGrant, URI tokenUri,
           ClientAuthentication clientAuthentication) throws MalformedURLException {

        HTTPRequest httpRequest = new HTTPRequest(HTTPRequest.Method.GET, tokenUri.toURL());
        httpRequest.setContentType(CommonContentTypes.APPLICATION_URLENCODED);
        clientAuthentication.applyTo(httpRequest);
        Map<String,String> params = httpRequest.getQueryParameters();
        params.putAll(authorizationCodeGrant.toParameters());
        if (clientRegistration.getScope() != null && !clientRegistration.getScope().isEmpty()) {
            params.put("scope", clientRegistration.getScope().stream().reduce((a, b) -> a + " " + b).get());
        }
        if (clientRegistration.getClientId() != null) {
            params.put("client_id", clientRegistration.getClientId());
        }
        httpRequest.setQuery(URLUtils.serializeParameters(params));
        httpRequest.setAccept(MediaType.APPLICATION_JSON_VALUE);
        httpRequest.setConnectTimeout(30000);
        httpRequest.setReadTimeout(30000);
        return httpRequest;
    }

}
