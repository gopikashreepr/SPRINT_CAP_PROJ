package utilities;

import java.util.UUID;

/**
 * Generates unique-per-run values so re-running the suite never fails on
 * "user already exists" style errors, and so no test data is a fixed
 * hardcoded literal inside a feature file.
 */
public class RandomDataGenerator {

    public static String uniqueUsername() {
        return "user_" + System.currentTimeMillis() + "_" + UUID.randomUUID().toString().substring(0, 6);
    }

    /**
     * Builds a password that always satisfies the BookStore API's password
     * policy (upper, lower, digit, special char, 8+ length) while still
     * being unique enough to vary between edge-case runs if needed.
     */
    public static String strongPassword() {
        return "Str0ng@" + UUID.randomUUID().toString().substring(0, 6);
    }
}
