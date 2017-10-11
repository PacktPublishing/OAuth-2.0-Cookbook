package com.packt.example.jweserver.oauth.jwt;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.DirectDecrypter;
import com.nimbusds.jose.crypto.DirectEncrypter;

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
        try {
            byte[] decodedKey = Base64.getDecoder().decode(encodedKeypair);
            SecretKey key = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");

            JWEHeader header = new JWEHeader(JWEAlgorithm.DIR, EncryptionMethod.A128GCM);
            Payload payloadObject = new Payload(payload);

            JWEObject jweObject = new JWEObject(header, payloadObject);
            jweObject.encrypt(new DirectEncrypter(key));

            return jweObject.serialize();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String, Object> decode(String base64EncodedKey, String content) {

        byte[] decodedKey = Base64.getDecoder().decode(base64EncodedKey);
        SecretKey key = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");

        try {
            JWEObject  jweObject = JWEObject.parse(content);

            jweObject.decrypt(new DirectDecrypter(key));

            Payload payload = jweObject.getPayload();
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectReader reader = objectMapper.readerFor(Map.class);
            return reader.with(DeserializationFeature.USE_LONG_FOR_INTS)
                    .readValue(payload.toString());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
