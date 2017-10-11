package example.packt.com.resourceownerpassword.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import example.packt.com.resourceownerpassword.client.oauth2.AccessToken;
import example.packt.com.resourceownerpassword.client.oauth2.TokenStore;
import example.packt.com.resourceownerpassword.login.AuthenticationManager;
import example.packt.com.resourceownerpassword.R;
import example.packt.com.resourceownerpassword.login.LoginService;
import example.packt.com.resourceownerpassword.login.User;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private LoginService loginService;

    private TokenStore tokenStore;

    private AuthenticationManager authenticationManager;

    private TextView usernameText;

    private TextView passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginService = new LoginService();
        tokenStore = new TokenStore(this);
        authenticationManager = new AuthenticationManager(this);

        Button loginButton = findViewById(R.id.main_login_button);
        loginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        usernameText = findViewById(R.id.main_username);
        passwordText = findViewById(R.id.main_password);

        String username = usernameText.getText().toString();
        String password = passwordText.getText().toString();

        loginService.loadUser(username, password, new LoginService.Callback() {
            @Override
            public void onSuccess(User user) {
                authenticationManager.authenticate();
                AccessToken accessToken = tokenStore.getToken();

                Intent intent;
                if (accessToken != null && !accessToken.hasExpired()) {
                    intent = new Intent(MainActivity.this, DashboardActivity.class);
                } else {
                    intent = new Intent(MainActivity.this, AuthorizationActivity.class);
                    intent.putExtra("username", user.getUsername());
                    intent.putExtra("password", user.getPassword());
                }
                startActivity(intent);
            }

            @Override
            public void onFailed(String message) {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
