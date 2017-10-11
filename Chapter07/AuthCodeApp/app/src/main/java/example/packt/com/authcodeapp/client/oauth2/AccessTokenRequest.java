package example.packt.com.authcodeapp.client.oauth2;

import java.util.HashMap;
import java.util.Map;

public class AccessTokenRequest {

    public static Map<String, String> fromCode(String code) {
        Map<String, String> map = new HashMap<>();
        map.put("code", code);
        map.put("scope", "read_profile");
        map.put("grant_type", "authorization_code");
        map.put("redirect_uri", AuthorizationRequest.REDIRECT_URI);
        return map;

    }

}
