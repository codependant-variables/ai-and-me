package com.codependentvariables.aiandme.services;

import com.codependentvariables.aiandme.model.User;

import org.apache.commons.codec.binary.Base32;

import javax.crypto.Mac;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Objects;

public class AuthService {
    private static AuthService instance;
    private final SecureRandom random = new SecureRandom();
    private final Base32 base32 = new Base32();

    private AuthService() {
        try {
            this.hmac = Mac.getInstance("Hmac" + TOTP_ALGORITHM);
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException("Could not find HMAC algorithm.", ex);
        }
    }

    public static AuthService getInstance() {
        if (instance == null) {
            instance = new AuthService();
        }
        return instance;
    }

    private byte[] getRandomBytes(int num) {
        byte[] bytes = new byte[num];
        random.nextBytes(bytes);
        return bytes;
    }

    public record HashResult(String hash, String salt) {}

    /**
     * Hashes the input string using a newly generated salt. Intended use is when setting a new password.
     * @param value to hash.
     * @return HashResult of hash string and salt string.
     */
    public HashResult hash(String value) {
        return hash(value, getRandomBytes(16));
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

    private final static long MS_IN_SECOND = 1000L;
    private final static long STEP_SECONDS = 30L;
    private final static int MAX_STEP_DRIFT = 2;
    public final static int TOTP_SIZE = 6;
    private final static String TOTP_ALGORITHM = "SHA512";
    private final static String TOTP_LABEL = "AI & Me";
    private final Mac hmac;

    public String createTotpSecret() {
        byte[] bytes = getRandomBytes(64);
        // TODO: encrypt?
        return base32.encodeAsString(bytes);
    }

    public String createTotpUri(String totpSecret, String userEmail) {
        return String.format("otpauth://totp/%s:%s/?secret=%s&algorithm=%s&digits=%s&counter=%d&issuer=%s", TOTP_LABEL, userEmail, totpSecret, TOTP_ALGORITHM, TOTP_SIZE, STEP_SECONDS, TOTP_LABEL);
    }

    /**
     * Get TOTP from current time.
     */
    public String getTotp(String totpSecret) {
        return getTotp(totpSecret, 0);
    }

    /**
     * Get TOTP from time adding a number of steps.
     */
    public String getTotp(String totpSecret, int addSteps) {
        byte[] totpSecretBytes = base32.decode(totpSecret);
        // TODO: decrypt?

        long unixTimestamp = System.currentTimeMillis() / MS_IN_SECOND;
        long steps = unixTimestamp / STEP_SECONDS + addSteps;

        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(steps);
        byte[] stepsBytes = buffer.array();

        try {
            SecretKeySpec macKey = new SecretKeySpec(totpSecretBytes, "RAW");
            this.hmac.init(macKey);
            byte[] hash = this.hmac.doFinal(stepsBytes);

            // --- DYNAMIC TRUNCATION ---
            // 1. Determine the offset
            int offset = hash[hash.length - 1] & 0xf;

            // 2. Extract 4 bytes starting at the offset
            int binary =
                    ((hash[offset] & 0x7f) << 24) |
                    ((hash[offset + 1] & 0xff) << 16) |
                    ((hash[offset + 2] & 0xff) << 8) |
                    (hash[offset + 3] & 0xff);

            // 3. Generate the N-digit code
            int otp = binary % (int)Math.pow(10, TOTP_SIZE);

            // 4. Zero-pad to ensure the correct length
            return String.format("%0" + TOTP_SIZE + "d", otp);
        } catch (GeneralSecurityException ex) {
            throw new RuntimeException("HMAC hash failed.", ex);
        }
    }

    public boolean compareTotp(User user, String totp) {
        String totpSecret = user.getTotpSecret();

        String currentTotp = getTotp(totpSecret);
        if (Objects.equals(currentTotp, totp)) {
            return true;
        }

        // Check +- MAX_STEP_DRIFT steps for if user device time drifts for up to 1 minute in each direction
        for (int i = 1; i < MAX_STEP_DRIFT + 1; i++) {
            String plusCurrentTotp = getTotp(totpSecret, i);
            if (Objects.equals(plusCurrentTotp, totp)) {
                return true;
            }

            String minusCurrentTotp = getTotp(totpSecret, -i);
            if (Objects.equals(minusCurrentTotp, totp)) {
                return true;
            }
        }

        return false;
    }
}
