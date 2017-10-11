package example.packt.com.embeddedapp.client.profile;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface UserProfileAPI {

    @GET("api/profile")
    Call<UserProfile> token(@Header("Authorization") String accessToken);

}
