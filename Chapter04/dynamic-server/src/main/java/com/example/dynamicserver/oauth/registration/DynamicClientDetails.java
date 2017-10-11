package com.example.dynamicserver.oauth.registration;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

public class DynamicClientDetails implements ClientDetails {
    private static final long serialVersionUID = 1L;

    private String clientId;
    private Set<String> resourceIds = new HashSet<>();
    private Set<String> authorizedGrantTypes = new HashSet<>();
    private String clientSecret;
    private Set<String> scopes = new HashSet<>();
    private Set<String> registeredRedirectUri = new HashSet<>();
    private Set<GrantedAuthority> authorities = new HashSet<>();
    private Integer accessTokenValiditySeconds;
    private Integer refreshTokenValiditySeconds;

    private Map<String, Object> additionalInformation = new HashMap<>();

    // additional fields
    private String softwareId;
    private String tokenEndpointAuthMethod;
    private Set<String> responseTypes = new HashSet<>();
    private String clientName;
    private String clientUri;
    private long clientSecretExpiresAt;

    public String getSoftwareId() { return softwareId; }

    public void setSoftwareId(String softwareId) {
        this.softwareId = softwareId;
        additionalInformation.put("software_id", softwareId);
    }

    public String getTokenEndpointAuthMethod() { return tokenEndpointAuthMethod; }

    public void setTokenEndpointAuthMethod(String tokenEndpointAuthMethod) {
        this.tokenEndpointAuthMethod = tokenEndpointAuthMethod;
        additionalInformation.put("token_endpoint_auth_method", tokenEndpointAuthMethod);
    }
    public Set<String> getResponseTypes() { return responseTypes; }

    public void setResponseTypes(Set<String> responseTypes) {
        this.responseTypes = responseTypes;
        additionalInformation.put("response_types", getResponseTypes());
    }
    public String getClientName() { return clientName; }

    public void setClientName(String clientName) {
        this.clientName = clientName;
        additionalInformation.put("client_name", clientName);
    }

    public String getClientUri() { return clientUri; }

    public void setClientUri(String clientUri) {
        this.clientUri = clientUri;
        additionalInformation.put("client_uri", clientUri);
    }

    public long getClientSecretExpiresAt() { return clientSecretExpiresAt; }
    public void setClientSecretExpiresAt(long clientSecretExpiresAt) {
        this.clientSecretExpiresAt = clientSecretExpiresAt;
        additionalInformation.put("client_secret_expires_at", Long.toString(clientSecretExpiresAt));
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public String getClientId() {
        return clientId;
    }

    public void addResourceId(String resourceId) {
        resourceIds.add(resourceId);
    }

    @Override
    public Set<String> getResourceIds() {
        return resourceIds;
    }

    @Override
    public boolean isSecretRequired() {
        return authorizedGrantTypes.containsAll(Arrays.asList("authorization_code", "password", "client_credentials"));
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    @Override
    public String getClientSecret() {
        return clientSecret;
    }

    @Override
    public boolean isScoped() {
        return scopes.size() > 0;
    }

    public void addScope(String scope) {
        this.scopes.add(scope);
    }

    @Override
    public Set<String> getScope() {
        return scopes;
    }

    public void addAuthorizedGrantTypes(String... authorizedGrantTypes) {
        for (String grantType : authorizedGrantTypes) {
            this.authorizedGrantTypes.add(grantType);
        }
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        return authorizedGrantTypes;
    }

    public void addRegisteredRedirectUri(String redirectUri) {
        registeredRedirectUri.add(redirectUri);
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        return registeredRedirectUri;
    }

    public void addAuthority(GrantedAuthority authority) {
        authorities.add(authority);
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return Collections.unmodifiableSet(authorities);
    }

    public void setAccessTokenValiditySeconds(Integer accessTokenValiditySeconds) {
        this.accessTokenValiditySeconds = accessTokenValiditySeconds;
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return accessTokenValiditySeconds;
    }

    public void setRefreshTokenValiditySeconds(Integer refreshTokenValiditySeconds) {
        this.refreshTokenValiditySeconds = refreshTokenValiditySeconds;
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return refreshTokenValiditySeconds;
    }

    @Override
    public boolean isAutoApprove(String scope) {
        return false;
    }

    public void addAdditionalInformation(String key, String value) {
        additionalInformation.put(key, value);
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return additionalInformation;
    }

}
