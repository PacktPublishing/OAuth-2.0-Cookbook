package example.packt.com.dynamicregisterapp.client;

import example.packt.com.dynamicregisterapp.client.registration.ClientCredentials;

public interface OnClientRegistrationResult {
    void onSuccessfulClientRegistration(ClientCredentials credentials);
    void onFailedClientRegistration(String s, Throwable t);
}
