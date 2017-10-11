package example.packt.com.implicitapp.presenter;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import example.packt.com.implicitapp.R;
import example.packt.com.implicitapp.client.ClientAPI;
import example.packt.com.implicitapp.client.oauth2.AccessToken;
import example.packt.com.implicitapp.client.oauth2.TokenStore;
import example.packt.com.implicitapp.client.profile.UserProfile;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileActivity extends AppCompatActivity {

    private TokenStore tokenStore;

    private TextView textName;
    private TextView textEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tokenStore = new TokenStore(this);

        textName = (TextView) findViewById(R.id.profile_name);
        textEmail = (TextView) findViewById(R.id.profile_email);

        AccessToken accessToken = tokenStore.getToken();


        Call<UserProfile> getUserProfile = ClientAPI.userProfile().token(accessToken.getValue());
        getUserProfile.enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                UserProfile userProfile = response.body();

                textName.setText(userProfile.getName());
                textEmail.setText(userProfile.getEmail());

            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {
                Log.e("ProfileActivity", "Error trying to retrieve user profile", t);
            }
        });

    }

}