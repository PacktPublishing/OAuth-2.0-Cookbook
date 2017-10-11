package com.packt.example.pop.oauth.resourceserver;

import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;

public class PoPAuthenticationManager implements AuthenticationManager {

    private AuthenticationManager authenticationManager;

    public PoPAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication authenticate(Authentication authentication)
        throws AuthenticationException {
        Authentication authenticationResult = authenticationManager
            .authenticate(authentication);

        if (authenticationResult.isAuthenticated()) {
            // validates nonce because JWT is already valid
            if (authentication instanceof PoPAuthenticationToken) {
                PoPAuthenticationToken popAuthentication = (PoPAuthenticationToken) authentication;

                // starts validating nonce here
                String nonce = popAuthentication.getNonce();
                if (nonce == null) {
                    throw new UnapprovedClientAuthenticationException(
                        "This request does not have a valid signed nonce");
                }

                String token = (String) popAuthentication.getPrincipal();

                System.out.println("access token:" + token);

                try {
                    JWT jwt = JWTParser.parse(token);
                    String publicKey = jwt.getJWTClaimsSet().getClaim("public_key").toString();
                    JWK jwk = JWK.parse(publicKey);

                    JWSObject jwsNonce = JWSObject.parse(nonce);
                    JWSVerifier verifier = new RSASSAVerifier((RSAKey) jwk);
                    if (!jwsNonce.verify(verifier)) {
                        throw new InvalidTokenException("Client hasn't possession of given token");
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }
        }

        return authenticationResult;
    }

}
