package example.packt.com.embeddedapp.presenter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.UUID;

import example.packt.com.embeddedapp.client.oauth2.AuthorizationRequest;
import example.packt.com.embeddedapp.client.oauth2.OAuth2StateManager;
import example.packt.com.embeddedapp.R;
import example.packt.com.embeddedapp.client.ClientAPI;

public class AuthorizationActivity extends AppCompatActivity {

    private WebView webView;
    private OAuth2StateManager oauth2StateManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);

        webView = (WebView) findViewById(R.id.authorization_webview);
        oauth2StateManager = new OAuth2StateManager(this);

        String state = UUID.randomUUID().toString();
        Uri authorizationUri = AuthorizationRequest.createAuthorizationURI(state);
        oauth2StateManager.saveState(state);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.contains("oauth2://profile/callback")) {
                    Intent intent = new Intent(
                        AuthorizationActivity.this, RedirectUriActivity.class);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                    finish();
                }
                return false;
            }
            @Override
            public boolean shouldOverrideUrlLoading(
                WebView view, WebResourceRequest request) {
                return false;
            }
        });
        webView.loadUrl(authorizationUri.toString());
    }

}