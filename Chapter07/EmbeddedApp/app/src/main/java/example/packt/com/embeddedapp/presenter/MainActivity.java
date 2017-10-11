package example.packt.com.embeddedapp.presenter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import example.packt.com.embeddedapp.R;
import example.packt.com.embeddedapp.client.oauth2.AccessToken;
import example.packt.com.embeddedapp.client.oauth2.TokenStore;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TokenStore tokenStore;

    private Button mainButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tokenStore = new TokenStore(this);
        mainButton = (Button) findViewById(R.id.main_button);

        mainButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == mainButton) {

            Intent intent;

            AccessToken accessToken = tokenStore.getToken();
            if (accessToken != null && !accessToken.isExpired()) {
                intent = new Intent(this, ProfileActivity.class);
            } else {
                intent = new Intent(this, AuthorizationActivity.class);
            }

            startActivity(intent);
        }
    }
}
