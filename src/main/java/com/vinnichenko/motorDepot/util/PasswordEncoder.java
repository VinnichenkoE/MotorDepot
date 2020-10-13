package com.vinnichenko.motorDepot.util;

import com.vinnichenko.motorDepot.util.exception.UtilException;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class PasswordEncoder {

    private static final int ITERATIONS = 20 * 1000;
    private static final int SALT_LENGTH = 32;
    private static final int DESIRED_KEY_LENGTH = 256;
    private static final String SEPARATOR = "\\$";
    private static final String SECURE_RANDOM_ALGORITHM = "SHA1PRNG";
    private static final String SECRET_KEY_FACTORY_ALGORITHM = "PBKDF2WithHmacSHA1";

    public static String getSaltedHash(String password) throws UtilException {
        byte[] salt;
        try {
            salt = SecureRandom.getInstance(SECURE_RANDOM_ALGORITHM).generateSeed(SALT_LENGTH);
        } catch (NoSuchAlgorithmException e) {
            throw new UtilException(e);
        }
        return Base64.encodeBase64String(salt) + SEPARATOR + hash(password, salt);
    }

    public static boolean check(String password, String stored) throws UtilException {
        String[] saltAndPass = stored.split(SEPARATOR);
        if (saltAndPass.length != 2) {
            throw new UtilException("The stored password have the form 'salt$hash'");
        }
        String hashOfInput = hash(password, Base64.decodeBase64(saltAndPass[0]));
        return hashOfInput.equals(saltAndPass[1]);
    }

    private static String hash(String password, byte[] salt) throws UtilException {
        if (password == null || password.length() == 0) {
            throw new UtilException("Empty passwords are not supported.");
        }
        SecretKey key;
        try {
            SecretKeyFactory f = SecretKeyFactory.getInstance(SECRET_KEY_FACTORY_ALGORITHM);
            key = f.generateSecret(new PBEKeySpec(
                    password.toCharArray(), salt, ITERATIONS, DESIRED_KEY_LENGTH)
            );
        } catch (GeneralSecurityException ex) {
            throw new UtilException(ex);
        }
        return Base64.encodeBase64String(key.getEncoded());
    }
}
