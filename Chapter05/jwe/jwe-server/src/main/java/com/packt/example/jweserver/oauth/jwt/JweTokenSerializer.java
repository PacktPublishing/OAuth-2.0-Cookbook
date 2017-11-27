package com.packt.example.jweserver.oauth.jwt;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.AESDecrypter;
import com.nimbusds.jose.crypto.AESEncrypter;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Map;

public class JweTokenSerializer {

    private String encodedKeypair;

    public JweTokenSerializer(String encodedKeypair) {
        this.encodedKeypair = encodedKeypair;
    }

public String encode(String payload) {
    JWEAlgorithm alg = JWEAlgorithm.A128KW;
    EncryptionMethod encryptionMethod = EncryptionMethod.A128GCM;

    try {
        byte[] decodedKey = Base64.getDecoder().decode(encodedKeypair);
        SecretKey key = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
        JWEObject jwe = new JWEObject(
                new JWEHeader(alg, encryptionMethod),
                new Payload(payload));
        jwe.encrypt(new AESEncrypter(key));
        return jwe.serialize();

    } catch (Exception e) {
        throw new RuntimeException(e);
    }
}

public Map<String, Object> decode(String base64EncodedKey, String content) {
    try {
        byte[] decodedKey = Base64.getDecoder().decode(base64EncodedKey);
        SecretKey key = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
        JWEObject jwe = JWEObject.parse(content);
        jwe.decrypt(new AESDecrypter(key));

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectReader reader = objectMapper.readerFor(Map.class);
        return reader.with(DeserializationFeature.USE_LONG_FOR_INTS)
                .readValue(jwe.getPayload().toString());
    } catch (Exception e) {
        throw new RuntimeException(e);
    }

}
}
