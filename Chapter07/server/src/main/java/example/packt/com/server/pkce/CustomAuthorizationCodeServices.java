package example.packt.com.server.pkce;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CustomAuthorizationCodeServices extends RandomValueAuthorizationCodeServices {

    protected final ConcurrentHashMap<String, OAuth2Authentication> authorizationCodeStore = new ConcurrentHashMap<String, OAuth2Authentication>();

    protected void store(String code, OAuth2Authentication authentication) {
        Map<String, String> parameters = authentication
                .getOAuth2Request().getRequestParameters();
        String codeChallenge = parameters.get("code_challenge");

        this.authorizationCodeStore.put(code, authentication);
    }

    public OAuth2Authentication remove(String code) {
        OAuth2Authentication auth = this.authorizationCodeStore.remove(code);
        return auth;
    }

}
