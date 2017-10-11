package example.packt.com.dynamicregisterapp.client.oauth2;

import android.net.Uri;

import example.packt.com.dynamicregisterapp.client.ClientAPI;
import example.packt.com.dynamicregisterapp.client.registration.ClientCredentials;

public class AuthorizationRequest {
    public static final String REDIRECT_URI
        = "oauth2://profile/callback";

    public static Uri createAuthorizationUri(String state, ClientCredentials clientCredentials) {
        return new Uri.Builder()
            .scheme("http")
            .encodedAuthority(ClientAPI.BASE_URL)
            .path("/oauth/authorize")
            .appendQueryParameter("client_id", clientCredentials.getClientId())
            .appendQueryParameter("response_type", "code")
            .appendQueryParameter("redirect_uri", REDIRECT_URI)
            .appendQueryParameter("scope", "read_profile")
            .appendQueryParameter("state", state)
            .build();
    }

}
