package example.packt.com.embeddedapp.presenter;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;

import example.packt.com.embeddedapp.client.oauth2.AccessToken;
import example.packt.com.embeddedapp.client.oauth2.OAuth2StateManager;
import example.packt.com.embeddedapp.R;
import example.packt.com.embeddedapp.client.oauth2.TokenStore;
import example.packt.com.embeddedapp.client.oauth2.URIUtils;
import example.packt.com.embeddedapp.client.profile.UserProfile;
import example.packt.com.embeddedapp.client.ClientAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    private TextView usernameText;
    private TextView emailText;

    private TokenStore tokenStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tokenStore = new TokenStore(this);
        AccessToken accessToken = tokenStore.getToken();

        usernameText = (TextView) findViewById(R.id.profile_username);
        emailText = (TextView) findViewById(R.id.profile_email);

        Call<UserProfile> profileCallback = ClientAPI.userProfile().token(accessToken.getValue());

        profileCallback.enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                UserProfile userProfile = response.body();

                usernameText.setText(userProfile.getName());
                emailText.setText(userProfile.getEmail());
            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "Error retrieving user profile",
                    Toast.LENGTH_SHORT).show();
            }
        });
    }
}
