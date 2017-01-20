package com.tk.service;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * PublicKeyCryptography
 *
 * @author: Trim Kadriu <trim.kadriu@gmail.com>
 */
public final class CryptoService {
    private static final String ALGORITHM = "RSA";

    public static String encrypt(String text, Key key) {
        String encrypted = null;
        byte[] cipherText = null;
        try {
            // get an RSA cipher object and print the provider
            final Cipher cipher = Cipher.getInstance(ALGORITHM);
            // encrypt the plain text using the public key
            cipher.init(Cipher.ENCRYPT_MODE, key);
            cipherText = cipher.doFinal(text.getBytes());
            encrypted = Base64.encodeBase64String(cipherText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encrypted;
    }

    public static String decrypt(String text, Key key) {
        byte[] encryptedText = Base64.decodeBase64(text);
        byte[] decryptedText = null;
        try {
            // get an RSA cipher object and print the provider
            final Cipher cipher = Cipher.getInstance(ALGORITHM);

            // decrypt the text using the private key
            cipher.init(Cipher.DECRYPT_MODE, key);
            decryptedText = cipher.doFinal(encryptedText);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return new String(decryptedText);
    }

    public static PublicKey getPublicKey(String key) {
        try {
            byte[] publicBytes = Base64.decodeBase64(key);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey pubKey = keyFactory.generatePublic(keySpec);
            return pubKey;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static PrivateKey getPrivateKey(String key) {
        try {
            byte[] publicBytes = Base64.decodeBase64(key);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(publicBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
            return privateKey;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getHash(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(text.getBytes("UTF-8"));
            byte[] digest = md.digest();
            return Base64.encodeBase64String(digest);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
