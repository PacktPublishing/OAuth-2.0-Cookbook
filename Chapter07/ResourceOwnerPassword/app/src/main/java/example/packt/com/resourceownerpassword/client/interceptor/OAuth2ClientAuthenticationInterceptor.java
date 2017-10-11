package example.packt.com.resourceownerpassword.client.interceptor;

import android.util.Base64;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class OAuth2ClientAuthenticationInterceptor implements Interceptor {
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
        String clientId = "clientapp";
        String clientSecret = "123456";

        String credentials = clientId + ":" + clientSecret;
        return "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
    }
}
