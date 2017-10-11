package example.packt.com.pkceapp.presenter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.UUID;

import example.packt.com.pkceapp.R;
import example.packt.com.pkceapp.client.oauth2.AccessToken;
import example.packt.com.pkceapp.client.oauth2.AuthorizationRequest;
import example.packt.com.pkceapp.client.oauth2.OAuth2StateManager;
import example.packt.com.pkceapp.client.oauth2.PkceManager;
import example.packt.com.pkceapp.client.oauth2.TokenStore;

public class MainActivity extends AppCompatActivity
    implements View.OnClickListener {

    private Button profileButton;
    private TokenStore tokenStore;
    private OAuth2StateManager stateManager;
    private AuthorizationRequest authorizationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tokenStore = new TokenStore(this);
        stateManager = new OAuth2StateManager(this);
        authorizationRequest = new AuthorizationRequest(new PkceManager(this));

        profileButton = (Button) findViewById(R.id.profile_button);
        profileButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        AccessToken accessToken = tokenStore.getToken();
        if (accessToken != null && !accessToken.isExpired()) {
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
            return;
        }

        // starts oauth flow if there is no valid access token
        String state = UUID.randomUUID().toString();
        stateManager.saveState(state);

        // creates the authorization URI to redirect user
        Uri authorizationUri = authorizationRequest.createAuthorizationUri(state);

        Intent authorizationIntent = new Intent(Intent.ACTION_VIEW);
        authorizationIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        authorizationIntent.setData(authorizationUri);
        startActivity(authorizationIntent);
    }

}
