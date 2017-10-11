package example.packt.com.dynamicregisterapp.client.registration;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ClientRegistrationAPI {

    @POST("/register")
    Call<ClientRegistrationResponse> register(@Body ClientRegistrationRequest request);

}
