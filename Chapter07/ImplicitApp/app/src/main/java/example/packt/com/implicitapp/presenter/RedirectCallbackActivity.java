package example.packt.com.implicitapp.presenter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.Map;

import example.packt.com.implicitapp.client.oauth2.AccessToken;
import example.packt.com.implicitapp.client.oauth2.OAuth2StateManager;
import example.packt.com.implicitapp.client.oauth2.TokenStore;
import example.packt.com.implicitapp.client.utils.URIUtils;

public class RedirectCallbackActivity  extends AppCompatActivity {

    private TokenStore tokenStore;
    private OAuth2StateManager stateManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tokenStore = new TokenStore(this);
        stateManager = new OAuth2StateManager(this);

        Uri callbackUri = Uri.parse(getIntent().getDataString());

        Map<String, String> parameters = URIUtils.getQueryParameters(callbackUri.getFragment());

        if (parameters.containsKey("error")) {
            Toast.makeText(this, parameters.get("error_description"), Toast.LENGTH_SHORT).show();
            return;
        }

        String state = parameters.get("state");

        // validates state parameter
        if (!stateManager.isValidState(state)) {
            Toast.makeText(this, "CSRF Attack detected", Toast.LENGTH_SHORT).show();
            return;
        }

        AccessToken accessToken = new AccessToken();
        accessToken.setValue(parameters.get("access_token"));
        accessToken.setExpiresIn(Long.parseLong(parameters.get("expires_in")));
        accessToken.setTokenType("bearer");

        tokenStore.save(accessToken);

        // go to profile activity
        Intent intentProfile = new Intent(this, ProfileActivity.class);
        startActivity(intentProfile);
        finish();
    }

}
