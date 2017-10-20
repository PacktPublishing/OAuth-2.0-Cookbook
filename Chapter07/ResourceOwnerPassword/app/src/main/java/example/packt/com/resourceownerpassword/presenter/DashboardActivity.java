package example.packt.com.resourceownerpassword.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import example.packt.com.resourceownerpassword.R;
import example.packt.com.resourceownerpassword.client.ClientAPI;
import example.packt.com.resourceownerpassword.client.oauth2.AccessToken;
import example.packt.com.resourceownerpassword.client.oauth2.TokenStore;
import example.packt.com.resourceownerpassword.client.profile.UserProfile;
import example.packt.com.resourceownerpassword.login.AuthenticationManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener {

    private TokenStore tokenStore;
    private TextView usernameText, emailText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        usernameText = (TextView) findViewById(R.id.profile_username);
        emailText = (TextView) findViewById(R.id.profile_email);

        tokenStore = new TokenStore(this);

        if (new AuthenticationManager(this).isAuthenticated()) {
            // add some fake user entries
            ListView listView = (ListView) findViewById(R.id.dashboard_entries);
            listView.setAdapter(new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1,
                new String[] {"Entry 1"}));

            // button to retrieve user profile
            Button profileButton = (Button) findViewById(R.id.profile_button);
            profileButton.setOnClickListener(this);
        } else {
            Intent loginIntent = new Intent(this, MainActivity.class);
            startActivity(loginIntent);
            finish();
        }

    }

    @Override
    public void onClick(View view) {

AccessToken accessToken = tokenStore.getToken();

if (accessToken != null && !accessToken.isExpired()) {
    Call<UserProfile> call = ClientAPI.userProfile()
        .token(accessToken.getValue());

    call.enqueue(new Callback<UserProfile>() {
        @Override
        public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
            UserProfile profile = response.body();
            usernameText.setText(profile.getName());
            emailText.setText(profile.getEmail());
        }

        @Override
        public void onFailure(Call<UserProfile> call, Throwable t) {
            Log.e("DashboardActivity", "Error reading user profile data", t);
        }
    });
}

    }


}
