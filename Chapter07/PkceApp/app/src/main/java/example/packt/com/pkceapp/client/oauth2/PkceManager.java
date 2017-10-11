package example.packt.com.pkceapp.client.oauth2;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class PkceManager {

    private final SharedPreferences preferences;

    public PkceManager(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public String createChallenge() {

        String codeVerifier = UUID.randomUUID().toString();

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] signedContent = messageDigest.digest(codeVerifier.getBytes());
            StringBuilder challenge = new StringBuilder();
            for (byte signedByte : signedContent) {
                challenge.append(String.format("%02X", signedByte));
            }

            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("code_verifier", codeVerifier);
            editor.commit();

            return challenge.toString().toLowerCase();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }

    public String getCodeVerifier() {
        String codeVerifier = preferences.getString("code_verifier", "");
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();

        return codeVerifier;
    }
}
