package example.packt.com.resourceownerpassword.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import example.packt.com.resourceownerpassword.client.ClientAPI;
import example.packt.com.resourceownerpassword.client.oauth2.AccessToken;
import example.packt.com.resourceownerpassword.client.oauth2.PasswordAccessTokenRequest;
import example.packt.com.resourceownerpassword.client.oauth2.TokenStore;
import example.packt.com.resourceownerpassword.login.AuthenticationManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthorizationActivity extends AppCompatActivity {

    private TokenStore tokenStore;
    private AuthenticationManager authenticationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tokenStore = new TokenStore(this);
        authenticationManager = new AuthenticationManager(this);

        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");

        if (authenticationManager.isAuthenticated()) {
            Call<AccessToken> call = ClientAPI.oauth2().token(
                PasswordAccessTokenRequest.from(username, password));

            call.enqueue(new Callback<AccessToken>() {
                @Override
                public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                    AccessToken accessToken = response.body();
                    tokenStore.save(accessToken);
                    Intent intent = new Intent(
                        AuthorizationActivity.this, DashboardActivity.class);
                    startActivity(intent);
                    finish();
                }
                @Override
                public void onFailure(Call<AccessToken> call, Throwable t) {
                    Log.e("AuthorizationActivity", "could not retrieve access token", t);
                }
            });
        }
    }

}