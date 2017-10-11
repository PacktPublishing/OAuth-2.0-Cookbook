package example.packt.com.implicitapp.client;

import example.packt.com.implicitapp.client.interceptor.BearerTokenHeaderInterceptor;
import example.packt.com.implicitapp.client.interceptor.ErrorInterceptor;
import example.packt.com.implicitapp.client.profile.UserProfileAPI;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class ClientAPI {

    public static final String BASE_URL = "10.0.2.2:8080";

    private final Retrofit retrofit;

    private ClientAPI() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(logging);
        client.addInterceptor(new BearerTokenHeaderInterceptor());
        client.addInterceptor(new ErrorInterceptor());

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
