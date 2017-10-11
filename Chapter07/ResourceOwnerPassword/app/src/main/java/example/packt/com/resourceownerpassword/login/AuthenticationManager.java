package example.packt.com.resourceownerpassword.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AuthenticationManager {
    private final SharedPreferences sharedPreferences;

    public AuthenticationManager(Context context) {
        sharedPreferences = PreferenceManager
            .getDefaultSharedPreferences(context.getApplicationContext());
    }

    public void authenticate() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("authenticated", true);
        editor.commit();
    }

    public boolean isAuthenticated() {
        return sharedPreferences.getBoolean("authenticated", false);
    }

}
