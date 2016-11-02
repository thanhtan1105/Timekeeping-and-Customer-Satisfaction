package com.timelinekeeping.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

/**
 * Created by HienTQSE60896 on 11/1/2016.
 */
public class CryptoUtils {

    private static final String TRANSFORMATION = "AES";
    private static final String ENCODING_KEY = "1234543444555666";

    public static String doEncrypt(String inputKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        return doEncrypt(ENCODING_KEY, inputKey);
    }

    public static String doDecrypt(String inputKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, IOException {
        return doDecrypt(ENCODING_KEY, inputKey);
    }

    public static String doEncrypt(String encodeKey, String inputKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        Key key = new SecretKeySpec(encodeKey.getBytes(), TRANSFORMATION);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = cipher.doFinal(inputKey.getBytes());
        String encrytedValue = new BASE64Encoder().encode(encVal);
        return encrytedValue;
    }

    public static String doDecrypt(String encodeKey, String inputKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, IOException {
        Key key = new SecretKeySpec(encodeKey.getBytes(), TRANSFORMATION);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptValue = new BASE64Decoder().decodeBuffer(inputKey);
        byte[] decValue = cipher.doFinal(decryptValue);
        return new String(decValue);


    }

    public static void main(String[] args) {
        try {
            System.out.println(doEncrypt(ENCODING_KEY, "AKIAIXAXWBASW4MLKFZQ"));
            System.out.println(doDecrypt(ENCODING_KEY, "IrSqKfsPAdcju80KqDw8n3F0i+SC8vEsRjXRVtjVv+c="));
            System.out.println(doEncrypt(ENCODING_KEY, "Qltke6qcSrflolW3qyHWKHy0KCtAiXbN554U50ys"));
            System.out.println(doDecrypt(ENCODING_KEY, "Mlfj5RfWr7nVKlvHIQQ7UKH8/6RM+h8DOGkcPtGkLet/Z/sWzgDAg/CKSGafJqkN"));
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
