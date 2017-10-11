package example.packt.com.dynamicregisterapp.client;

import example.packt.com.dynamicregisterapp.client.interceptor.BearerTokenHeaderInterceptor;
import example.packt.com.dynamicregisterapp.client.interceptor.ErrorInterceptor;
import example.packt.com.dynamicregisterapp.client.interceptor.OAuth2ClientAuthenticationInterceptor;
import example.packt.com.dynamicregisterapp.client.oauth2.OAuth2API;
import example.packt.com.dynamicregisterapp.client.registration.ClientCredentials;
import example.packt.com.dynamicregisterapp.client.registration.ClientRegistrationAPI;
import example.packt.com.dynamicregisterapp.client.profile.UserProfileAPI;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class ClientAPI {

    public static final String BASE_URL = "10.0.2.2:8080";

    private final Retrofit retrofit;

    public static UserProfileAPI userProfile() {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(new ErrorInterceptor());
        client.addInterceptor(new BearerTokenHeaderInterceptor());
        ClientAPI api = new ClientAPI(client);
        return api.retrofit.create(UserProfileAPI.class);
    }

    public static OAuth2API oauth2(ClientCredentials clientCredentials) {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(new ErrorInterceptor());
        client.addInterceptor(new OAuth2ClientAuthenticationInterceptor(
                clientCredentials.getClientId(),
                clientCredentials.getClientSecret()));
        ClientAPI api = new ClientAPI(client);
        return api.retrofit.create(OAuth2API.class);
    }

    public static ClientRegistrationAPI registration() {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(new ErrorInterceptor());
        ClientAPI api = new ClientAPI(client);
        return api.retrofit.create(ClientRegistrationAPI.class);
    }

    private ClientAPI(OkHttpClient.Builder httpClientBuilder) {
        retrofit = new Retrofit.Builder()
            .baseUrl("http://" + BASE_URL)
            .addConverterFactory(JacksonConverterFactory.create())
            .client(httpClientBuilder.build())
            .build();
    }

}
