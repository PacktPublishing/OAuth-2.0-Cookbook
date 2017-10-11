package example.packt.com.server.registration;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class RedirectFlowSpecification {

    public boolean isSatisfiedBy(ClientRegistrationRequest clientMetadata) {
        List<String> flowsWithRedirection = Arrays.asList("authorization_code", "implicit");

        boolean hasFlowWithRedirection = clientMetadata.getGrantTypes().stream()
            .filter(grantType -> flowsWithRedirection.contains(grantType))
            .findAny().isPresent();

        if (hasFlowWithRedirection) {
            return clientMetadata.getRedirectUris().size() > 0;
        }

        return false;
    }
}
