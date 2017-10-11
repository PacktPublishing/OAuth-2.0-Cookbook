package example.packt.com.pkceapp.client.oauth2;

import android.net.Uri;

import example.packt.com.pkceapp.client.ClientAPI;

public class AuthorizationRequest {

    public static final String REDIRECT_URI
            = "oauth2://profile/callback";

    private final PkceManager pixyManager;

    public AuthorizationRequest(PkceManager pixyManager) {
        this.pixyManager = pixyManager;
    }

    public Uri createAuthorizationUri(String state) {
        return new Uri.Builder()
            .scheme("http")
            .encodedAuthority(ClientAPI.BASE_URL)
            .path("/oauth/authorize")
            .appendQueryParameter("client_id", "clientapp")
            .appendQueryParameter("response_type", "code")
            .appendQueryParameter("redirect_uri", REDIRECT_URI)
            .appendQueryParameter("scope", "read_profile")
            .appendQueryParameter("state", state)
            .appendQueryParameter("code_challenge", pixyManager.createChallenge())
            .appendQueryParameter("code_challenge_method", "S256")
            .build();
    }
}
