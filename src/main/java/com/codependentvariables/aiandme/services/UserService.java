package com.codependentvariables.aiandme.services;

import com.codependentvariables.aiandme.model.User;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Objects;

public class UserService {
    private final static SecureRandom random = new SecureRandom();

    public record HashResult(String hash, String salt) {}

    /**
     * Hashes the input and returns a tuple of hash and salt.
     * @return <hash, salt>
     */
    public static HashResult hash(String value) {
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        return hash(value, salt);
    }

    public static HashResult hash(String value, String salt) {
        return hash(value, Base64.getDecoder().decode(salt));
    }

    public static HashResult hash(String value, byte[] salt) {
        KeySpec spec = new PBEKeySpec(value.toCharArray(), salt, 600000, 256);
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            byte[] hash = factory.generateSecret(spec).getEncoded();

            return new HashResult(Base64.getEncoder().encodeToString(hash), Base64.getEncoder().encodeToString(salt));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            throw new RuntimeException("Error hashing password", ex);
        }
    }

    public static boolean comparePassword(User user, String password) {
        if (user == null || user.getPassword() == null || user.getSalt() == null)
            return false;

        String computedHash = hash(password, user.getSalt()).hash();

        return Objects.equals(user.getPassword(), computedHash);
    }
}