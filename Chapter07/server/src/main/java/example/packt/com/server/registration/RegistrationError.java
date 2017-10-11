package example.packt.com.server.registration;

public class RegistrationError {

    public final static String INVALID_CLIENT_METADATA = "invalid_client_metadata";
    public final static String INVALID_REDIRECT_URI = "invalid_redirect_uri";
    public final static String INVALID_SOFTWARE_STATEMENT = "invalid_software_statement";
    public final static String UNAPPROVED_SOFTWARE_STATEMENT = "unapproved_software_statement";

    private String error;
    private String errorDescription;

    public RegistrationError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

}
