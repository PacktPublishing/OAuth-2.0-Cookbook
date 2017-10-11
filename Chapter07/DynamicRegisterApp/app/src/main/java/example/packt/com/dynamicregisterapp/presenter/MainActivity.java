package example.packt.com.dynamicregisterapp.presenter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.UUID;

import example.packt.com.dynamicregisterapp.R;
import example.packt.com.dynamicregisterapp.client.oauth2.AccessToken;
import example.packt.com.dynamicregisterapp.client.oauth2.AuthorizationRequest;
import example.packt.com.dynamicregisterapp.client.oauth2.OAuth2StateManager;
import example.packt.com.dynamicregisterapp.client.oauth2.TokenStore;
import example.packt.com.dynamicregisterapp.client.registration.ClientCredentials;
import example.packt.com.dynamicregisterapp.client.registration.ClientCredentialsStore;
import example.packt.com.dynamicregisterapp.client.ClientRegistrationService;
import example.packt.com.dynamicregisterapp.client.OnClientRegistrationResult;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener, OnClientRegistrationResult {

    private Button profileButton;
    private TokenStore tokenStore;
    private OAuth2StateManager oauth2StateManager;
    private ClientRegistrationService clientRegistrationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tokenStore = new TokenStore(this);
        oauth2StateManager = new OAuth2StateManager(MainActivity.this);
        clientRegistrationService = new ClientRegistrationService();

        profileButton = (Button) findViewById(R.id.profile_button);
        profileButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        ClientCredentialsStore credentialsStore = ClientCredentialsStore.getInstance();
        ClientCredentials clientCredentials = credentialsStore.get();

        if (clientCredentials != null) {
            proceed(clientCredentials);
        } else {
            clientRegistrationService.registerClient(this);
        }
    }

    @Override
    public void onSuccessfulClientRegistration(ClientCredentials credentials) {
        ClientCredentialsStore store = ClientCredentialsStore.getInstance();
        store.save(credentials);
        proceed(credentials);
    }

    @Override
    public void onFailedClientRegistration(String s, Throwable t) {
        Log.e("MainActivity", "Error trying to register client credentials", t);
    }

    private void proceed(ClientCredentials clientCredentials) {
        String state = UUID.randomUUID().toString();
        oauth2StateManager.saveState(state);

        Uri authorizationUri = AuthorizationRequest.createAuthorizationUri(state, clientCredentials);
        AccessToken token = tokenStore.getToken();
        final Intent intent;
        if (token != null && !token.isExpired()) {
            intent = new Intent(this, ProfileActivity.class);
        } else {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            intent.setData(authorizationUri);
        }
        startActivity(intent);
    }

}
