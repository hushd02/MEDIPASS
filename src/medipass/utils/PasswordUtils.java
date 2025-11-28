package medipass.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Utilitaire simple pour hacher/valider des mots de passe.
 * NOTE: pour production, utilisez BCrypt/Argon2. Ce helper améliore
 * la situation par rapport au stockage en clair (SHA-256).
 */
public final class PasswordUtils {

    private PasswordUtils() {
    }

    public static String hash(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashed = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hashed);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 not available", e);
        }
    }

    public static boolean verify(String rawPassword, String storedHash) {
        if (rawPassword == null || storedHash == null)
            return false;
        return hash(rawPassword).equals(storedHash);
    }
}
