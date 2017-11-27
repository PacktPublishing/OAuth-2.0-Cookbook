package com.packt.example.popclient.oauth;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.RSAKey;
import org.springframework.security.oauth2.common.util.RandomValueStringGenerator;
import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Component
public class JwkKeyPairManager {

    private final JWK clientJwk;

    public JwkKeyPairManager() {
        KeyPair keyPair = createRSA256KeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RandomValueStringGenerator random = new RandomValueStringGenerator();
        RSAKey.Builder builder = new RSAKey.Builder(publicKey);
        builder.keyID(random.generate());
        builder.privateKey(privateKey);
        this.clientJwk = builder.build();
    }

    public JWK createJWK() {
        return clientJwk.toPublicJWK();
    }

    public String getSignedContent(String content) {
        Payload contentPayload = new Payload(content);

        try {
            RSASSASigner rsa = new RSASSASigner((RSAPrivateKey) clientJwk);
            JWSAlgorithm alg = JWSAlgorithm.RS256;
            JWSHeader header = new JWSHeader.Builder(alg)
                .keyID(clientJwk.getKeyID())
                .build();
            JWSObject jws = new JWSObject(header, contentPayload);
            jws.sign(rsa);
            return jws.serialize();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private KeyPair createRSA256KeyPair() {
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048);
            return generator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}
