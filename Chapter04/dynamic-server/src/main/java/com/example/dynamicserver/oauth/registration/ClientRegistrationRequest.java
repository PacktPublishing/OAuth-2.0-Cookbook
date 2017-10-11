package com.example.dynamicserver.oauth.registration;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.validator.constraints.NotBlank;

public class ClientRegistrationRequest {

    @NotBlank
    private String clientName;

    @NotBlank
    private String clientUri;

    @NotBlank
    private String scope;

    @NotBlank
    private String softwareId;

    private Set<String> redirectUris = new HashSet<>();

    private Set<String> grantTypes = new HashSet<>();

    public Set<String> getRedirectUris() {
        return redirectUris;
    }

    public void setRedirectUris(Set<String> redirectUris) {
        this.redirectUris = redirectUris;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientUri() {
        return clientUri;
    }

    public void setClientUri(String clientUri) {
        this.clientUri = clientUri;
    }

    public String getSoftwareId() {
        return softwareId;
    }

    public void setSoftwareId(String softwareId) {
        this.softwareId = softwareId;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public Set<String> getGrantTypes() {
        return grantTypes;
    }

    public void setGrantTypes(Set<String> grantTypes) {
        this.grantTypes = grantTypes;
    }

}
