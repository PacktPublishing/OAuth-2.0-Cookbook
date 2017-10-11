package example.packt.com.dynamicregisterapp.client;

import android.support.annotation.NonNull;

import example.packt.com.dynamicregisterapp.client.oauth2.AuthorizationRequest;
import example.packt.com.dynamicregisterapp.client.registration.ClientCredentials;
import example.packt.com.dynamicregisterapp.client.registration.ClientRegistrationRequest;
import example.packt.com.dynamicregisterapp.client.registration.ClientRegistrationResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientRegistrationService {

    public void registerClient(final OnClientRegistrationResult registrationResult) {

        ClientRegistrationRequest request = new ClientRegistrationRequest();
        request.setScope("read_profile");
        request.setClientName("android-app");
        request.setClientUri("http://adolfoeloy.com.br/en");
        request.setSoftwareId("android-packt");
        request.getGrantTypes().add("authorization_code");
        request.getRedirectUris().add(AuthorizationRequest.REDIRECT_URI);

        Call<ClientRegistrationResponse> call = ClientAPI.registration().register(request);
        call.enqueue(new Callback<ClientRegistrationResponse>() {
            @Override
            public void onResponse(Call<ClientRegistrationResponse> call,
                Response<ClientRegistrationResponse> response) {
                ClientRegistrationResponse credentialsResponse = response.body();
                registrationResult.onSuccessfulClientRegistration(
                    createClientCredentials(credentialsResponse));
            }
            @Override
            public void onFailure(Call<ClientRegistrationResponse> call, Throwable t) {
                registrationResult.onFailedClientRegistration(
                        "Failed on trying to register client", t);
            }
        });
    }

    @NonNull
    private ClientCredentials createClientCredentials(
        ClientRegistrationResponse credentialsResponse) {
        ClientCredentials credentials = new ClientCredentials();
        credentials.setClientId(credentialsResponse.getClientId());
        credentials.setClientSecret(credentialsResponse.getClientSecret());
        return credentials;
    }
}
