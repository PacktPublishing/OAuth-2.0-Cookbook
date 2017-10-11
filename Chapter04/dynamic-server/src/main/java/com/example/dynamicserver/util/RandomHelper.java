package com.example.dynamicserver.util;

import java.math.BigInteger;
import java.security.SecureRandom;

import org.springframework.stereotype.Component;

/**
 * Created as a component bean because it
 * depends on expensive object to be created.
 */
@Component
public class RandomHelper {

    private SecureRandom random;

    public RandomHelper() {
        random = new SecureRandom();
    }

    public String nextString(int size, int radix) {
        double numberOf = Math.log10(radix) / Math.log10(2);
        int bits = (int) (size * numberOf);
        int mod = (int) (bits % numberOf);
        return new BigInteger(bits + mod, random).toString(radix);
    }

}
