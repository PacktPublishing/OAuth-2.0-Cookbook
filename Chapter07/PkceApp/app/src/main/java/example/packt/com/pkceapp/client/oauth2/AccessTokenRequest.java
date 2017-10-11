package example.packt.com.pkceapp.client.oauth2;

import java.util.HashMap;
import java.util.Map;

public class AccessTokenRequest {

    private final PkceManager pixyManager;

    public AccessTokenRequest(PkceManager pixyManager) {
        this.pixyManager = pixyManager;
    }

    public Map<String, String> from(String code) {
        Map<String, String> map = new HashMap<>();
        map.put("code", code);
        map.put("code_verifier", pixyManager.getCodeVerifier());
        map.put("scope", "read_profile");
        map.put("grant_type", "authorization_code");
        map.put("redirect_uri", "oauth2://profile/callback");
        return map;
    }

}
