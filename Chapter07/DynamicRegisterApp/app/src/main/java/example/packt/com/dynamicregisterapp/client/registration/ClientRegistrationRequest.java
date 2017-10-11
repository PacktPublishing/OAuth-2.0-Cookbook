package example.packt.com.dynamicregisterapp.client.registration;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashSet;
import java.util.Set;

public class ClientRegistrationRequest {
    @JsonProperty("client_name")
    private String clientName;

    @JsonProperty("client_uri")
    private String clientUri;

    @JsonProperty("scope")
    private String scope;

    @JsonProperty("software_id")
    private String softwareId;

    @JsonProperty("redirect_uris")
    private Set<String> redirectUris = new HashSet<>();

    @JsonProperty("grant_types")
    private Set<String> grantTypes = new HashSet<>();

    public String getClientName() {
        return clientName;
    }

    public ClientRegistrationRequest setClientName(String clientName) {
        this.clientName = clientName;
        return this;
    }

    public String getClientUri() {
        return clientUri;
    }

    public ClientRegistrationRequest setClientUri(String clientUri) {
        this.clientUri = clientUri;
        return this;
    }

    public String getScope() {
        return scope;
    }

    public ClientRegistrationRequest setScope(String scope) {
        this.scope = scope;
        return this;
    }

    public String getSoftwareId() {
        return softwareId;
    }

    public ClientRegistrationRequest setSoftwareId(String softwareId) {
        this.softwareId = softwareId;
        return this;
    }

    public Set<String> getRedirectUris() {
        return redirectUris;
    }

    public ClientRegistrationRequest setRedirectUris(Set<String> redirectUris) {
        this.redirectUris = redirectUris;
        return this;
    }

    public Set<String> getGrantTypes() {
        return grantTypes;
    }

    public ClientRegistrationRequest setGrantTypes(Set<String> grantTypes) {
        this.grantTypes = grantTypes;
        return this;
    }
}
