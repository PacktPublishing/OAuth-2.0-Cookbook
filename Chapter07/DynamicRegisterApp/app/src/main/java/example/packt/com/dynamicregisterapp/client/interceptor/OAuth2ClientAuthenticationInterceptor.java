package example.packt.com.dynamicregisterapp.client.interceptor;

import android.util.Base64;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * This version of OAuth2ClientAuthenticationInterceptor can' have hard coded client credentials
 */
public class OAuth2ClientAuthenticationInterceptor implements Interceptor {
    private String username;
    private String password;

    public OAuth2ClientAuthenticationInterceptor(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        Request authenticatedRequest = request.newBuilder()
            .addHeader("Authorization", getEncodedAuthorization())
            .addHeader("Content-Type", "application/x-www-form-urlencoded")
            .method(request.method(), request.body())
            .build();

        return chain.proceed(authenticatedRequest);
    }

    private String getEncodedAuthorization() {
        String credentials = username + ":" + password;
        return "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
    }
}
