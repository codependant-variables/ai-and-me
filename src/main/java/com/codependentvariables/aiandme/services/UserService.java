package com.codependentvariables.aiandme.services;

import com.codependentvariables.aiandme.model.IUserDAO;
import com.codependentvariables.aiandme.model.SqliteUserDAO;
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
    private static UserService instance;
    private final SecureRandom random = new SecureRandom();
    private final IUserDAO userDAO;

    private UserService() {
        this(new SqliteUserDAO());
    }

    // Package-private constructor for unit tests
    UserService(IUserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public record HashResult(String hash, String salt) {}

    /**
     * Hashes the input string using a newly generated salt. Intended use is when setting a new password.
     * @param value to hash.
     * @return HashResult of hash string and salt string.
     */
    public HashResult hash(String value) {
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        return hash(value, salt);
    }

    /**
     * Hashes the input string using the provided salt. Decodes the string to a byte array for ease of use. Intended use is when creating a hash from an existing salt for comparison, e.g. comparing a password.
     * @param value to hash.
     * @param salt to hash with.
     * @return HashResult of hash string and salt string.
     */
    public HashResult hash(String value, String salt) {
        return hash(value, Base64.getDecoder().decode(salt));
    }

    /**
     * Shared internal method for hashing a string using a salt as byte array.
     * @param value to hash.
     * @param salt to hash with.
     * @return HashResult of hash string and salt string.
     */
    private HashResult hash(String value, byte[] salt) {
        KeySpec spec = new PBEKeySpec(value.toCharArray(), salt, 600000, 256); // OWASP recommendation to use a work factor of 600,000 -  https://cheatsheetseries.owasp.org/cheatsheets/Password_Storage_Cheat_Sheet.html
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            byte[] hash = factory.generateSecret(spec).getEncoded();

            return new HashResult(Base64.getEncoder().encodeToString(hash), Base64.getEncoder().encodeToString(salt));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            throw new RuntimeException("Error hashing password", ex);
        }
    }

    /**
     * Compares the user's password against a provided string.
     * @param user User whose password to compare.
     * @param password Password to compare to the user's.
     * @return If password equals that of the user.
     */
    public boolean comparePassword(User user, String password) {
        if (user == null || user.getPassword() == null || user.getSalt() == null)
            return false;

        String computedHash = hash(password, user.getSalt()).hash();

        return Objects.equals(user.getPassword(), computedHash);
    }

    public User getByEmail(String email) {
        return userDAO.getByEmail(email);
    }

    public boolean isUniqueEmail(String email) {
        return userDAO.getByEmail(email) == null;
    }

    public User createUser(String name, String email, String password) {
        HashResult hashResult = hash(password);
        User user = new User(name, email, hashResult.hash, hashResult.salt);
        userDAO.add(user);
        return user;
    }
}