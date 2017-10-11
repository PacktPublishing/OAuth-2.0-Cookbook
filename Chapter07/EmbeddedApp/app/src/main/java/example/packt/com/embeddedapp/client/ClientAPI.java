package example.packt.com.embeddedapp.client;

import example.packt.com.embeddedapp.client.profile.UserProfileAPI;
import example.packt.com.embeddedapp.client.interceptor.BearerTokenHeaderInterceptor;
import example.packt.com.embeddedapp.client.interceptor.ErrorInterceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Represents the OAuth2 Provider API (with it' respective protected API)
 */
public class ClientAPI {

    private final Retrofit retrofit;

    public static final String BASE_URL = "10.0.2.2:8080";

    private ClientAPI() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(logging);
        client.addInterceptor(new ErrorInterceptor());
        client.addInterceptor(new BearerTokenHeaderInterceptor());

        retrofit = new Retrofit.Builder()
                .baseUrl("http://" + BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .client(client.build())
                .build();

    }

    public static UserProfileAPI userProfile() {
        ClientAPI api = new ClientAPI();
        return api.retrofit.create(UserProfileAPI.class);
    }

}
