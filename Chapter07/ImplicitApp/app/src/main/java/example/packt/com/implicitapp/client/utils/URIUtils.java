package example.packt.com.implicitapp.client.utils;

import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

public class URIUtils {

    @NonNull
    public static Map<String, String> getQueryParameters(String fragment) {
        String[] queryParams = fragment.split("&");
        Map<String, String> parameters = new HashMap<>();
        for (String keyValue : queryParams) {
            String[] parameter = keyValue.split("=");
            String key = parameter[0];
            String value = parameter[1];
            parameters.put(key, value);
        }
        return parameters;
    }
}
