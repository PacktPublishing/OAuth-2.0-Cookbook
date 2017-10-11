package example.packt.com.dynamicregisterapp.client.registration;

public final class ClientCredentialsStore {
    private static ClientCredentialsStore instance;
    private ClientCredentials credentials = null;

    private ClientCredentialsStore() {}

    public static ClientCredentialsStore getInstance() {
        if (instance == null) {
            instance = new ClientCredentialsStore();
        }
        return instance;
    }

    public void save(ClientCredentials credentials) {
        this.credentials = credentials;
    }

    public ClientCredentials get() {
        return this.credentials;
    }
}
