package Utilities;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * Băm và kiểm tra mật khẩu bằng PBKDF2-HMAC-SHA256 (có sẵn trong JDK).
 *
 * Định dạng lưu trong DB: PBKDF2$<số vòng lặp>$<salt Base64>$<hash Base64>
 * Mỗi mật khẩu có một salt ngẫu nhiên riêng, nên 2 người cùng mật khẩu
 * vẫn cho ra 2 chuỗi hash khác nhau.
 */
public final class PasswordUtil {

    private static final String PREFIX = "PBKDF2";
    private static final String ALGORITHM = "PBKDF2WithHmacSHA256";
    private static final int ITERATIONS = 210_000;
    private static final int SALT_BYTES = 16;
    private static final int HASH_BITS = 256;
    private static final SecureRandom RANDOM = new SecureRandom();

    private PasswordUtil() {
    }

    /** Băm mật khẩu thô thành chuỗi để lưu vào DB. */
    public static String hash(String rawPassword) {
        byte[] salt = new byte[SALT_BYTES];
        RANDOM.nextBytes(salt);
        byte[] hash = pbkdf2(rawPassword, salt, ITERATIONS);
        return PREFIX + "$" + ITERATIONS + "$"
                + Base64.getEncoder().encodeToString(salt) + "$"
                + Base64.getEncoder().encodeToString(hash);
    }

    /** So khớp mật khẩu người dùng nhập với chuỗi hash đã lưu. */
    public static boolean verify(String rawPassword, String stored) {
        if (rawPassword == null || !isHashed(stored)) {
            return false;
        }
        try {
            String[] parts = stored.split("\\$");
            int iterations = Integer.parseInt(parts[1]);
            byte[] salt = Base64.getDecoder().decode(parts[2]);
            byte[] expected = Base64.getDecoder().decode(parts[3]);
            byte[] actual = pbkdf2(rawPassword, salt, iterations);
            // So sánh thời gian không đổi, tránh timing attack
            return MessageDigest.isEqual(expected, actual);
        } catch (RuntimeException e) {
            return false;
        }
    }

    /** Chuỗi có đúng định dạng hash của lớp này hay chưa (dùng để tránh băm 2 lần). */
    public static boolean isHashed(String value) {
        return value != null && value.startsWith(PREFIX + "$") && value.split("\\$").length == 4;
    }

    private static byte[] pbkdf2(String password, byte[] salt, int iterations) {
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, HASH_BITS);
        try {
            return SecretKeyFactory.getInstance(ALGORITHM).generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new IllegalStateException("Không thể băm mật khẩu", e);
        } finally {
            spec.clearPassword();
        }
    }
}
