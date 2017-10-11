package example.packt.com.server.registration;

import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.SecureRandom;

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
