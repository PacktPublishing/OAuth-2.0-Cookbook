package com.packt.example.oauth2provider.client;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

public class Application implements ClientDetails {
    private static final long serialVersionUID = 1L;

    private String clientId;

    private String clientSecret;

    private ClientType clientType;

    private Set<String> resourceIds = new HashSet<>();

    private Set<String> scope = new HashSet<>();

    private Set<String> webServerRedirectUri = new HashSet<>();

    private int accessTokenValidity;

    private Map<String, Object> additionalInformation = new HashMap<>();

    public void setName(String name) {
        additionalInformation.put("name", name);
    }

    public void setClientType(ClientType clientType) {
        additionalInformation.put("client_type", clientType.name());
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public void setAccessTokenValidity(int accessTokenValidity) {
        this.accessTokenValidity = accessTokenValidity;
    }

    public void addRedirectUri(String redirectUri) {
        this.webServerRedirectUri.add(redirectUri);
    }

    public void addScope(String scope) {
        this.scope.add(scope);
    }

    public void addResourceId(String resourceId) {
        this.resourceIds.add(resourceId);
    }

    @Override
    public String getClientId() {
        return clientId;
    }

    @Override
    public Set<String> getResourceIds() {
        return Collections.unmodifiableSet(resourceIds);
    }

    @Override
    public boolean isSecretRequired() {
        return clientType == ClientType.CONFIDENTIAL;
    }

    @Override
    public String getClientSecret() {
        return clientSecret;
    }

    @Override
    public boolean isScoped() {
        return scope.size() > 0;
    }

    @Override
    public Set<String> getScope() {
        return Collections.unmodifiableSet(scope);
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        Set<String> grantTypes = new HashSet<>();
        grantTypes.add("authorization_code");
        grantTypes.add("refresh_token");
        return grantTypes;
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        return Collections.unmodifiableSet(webServerRedirectUri);
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return new HashSet<>();
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return accessTokenValidity;
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return null;
    }

    @Override
    public boolean isAutoApprove(String scope) {
        return false;
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return additionalInformation;
    }

}
