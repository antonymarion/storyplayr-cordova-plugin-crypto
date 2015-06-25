package com.storyplayr.cordova;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;

public class Crypto {

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";

    public static void cryptoFile(int cipherMode, String key, File inputFile,
                                  File outputFile) throws CryptoException {
        try {
            Key secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(cipherMode, secretKey);

            FileInputStream inputStream = new FileInputStream(inputFile);
            byte[] inputBytes = new byte[(int) inputFile.length()];
            inputStream.read(inputBytes);

            byte[] outputBytes = cipher.doFinal(inputBytes);

            FileOutputStream outputStream = new FileOutputStream(outputFile);
            outputStream.write(outputBytes);

            inputStream.close();
            outputStream.close();
        } catch (Exception ex) {
            throw new CryptoException("Error encrypting/decrypting file", ex);
        }
    }

    public static String cryptoString(int cipherMode, String key, String inputString) throws CryptoException {
        try {

            Key secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(cipherMode, secretKey);

            byte[] input;
            if(cipherMode == Cipher.DECRYPT_MODE) {
                input = android.util.Base64.decode(inputString, Base64.DEFAULT);
            }
            else {
                input = inputString.getBytes();
            }

            byte[] output = new byte[cipher.getOutputSize(input.length)];
            int size = cipher.update(input, 0, input.length, output, 0);
            size += cipher.doFinal(output, size);
            if(cipherMode == Cipher.ENCRYPT_MODE) {
                return android.util.Base64.encodeToString(output, Base64.DEFAULT);
            }
            else {
                return new String(output);
            }


        } catch (Exception ex) {
            throw new CryptoException("Error encrypting/decrypting string", ex);
        }
    }

}