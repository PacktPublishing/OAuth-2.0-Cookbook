package example.packt.com.resourceownerpassword.client.oauth2;

import java.util.HashMap;
import java.util.Map;

public class PasswordAccessTokenRequest {

    public static Map<String, String> from(String username, String password) {
        Map<String, String> map = new HashMap<>();
        map.put("scope", "read_profile");
        map.put("grant_type", "password");
        map.put("username", username);
        map.put("password", password);
        return map;
    }

}
