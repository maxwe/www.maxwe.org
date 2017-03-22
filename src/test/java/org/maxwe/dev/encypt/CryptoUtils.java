package org.maxwe.dev.encypt;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.Key;
import java.util.Base64;

/**
 * Created by Pengwei Ding on 2016-06-02 15:32.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class CryptoUtils {
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/CBC/ISO10126Padding";

    public static void encrypt(String key, File inputFile, File outputFile) throws Exception {
//        doCrypto(Cipher.ENCRYPT_MODE, key, inputFile, outputFile);
        try {
            Key secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            IvParameterSpec iv = new IvParameterSpec(key.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, secretKey,iv);

            FileInputStream inputStream = new FileInputStream(inputFile);
            byte[] inputBytes = new byte[(int) inputFile.length()];
            inputStream.read(inputBytes);
//              byte[] inputBytes = "kkkkkooooo".getBytes();
//            byte[] inputBytes = "Given final block not properly padded".getBytes();
            byte[] outputBytes = cipher.doFinal(inputBytes);
            String s = new String(outputBytes);
            byte[] encode = Base64.getEncoder().encode(outputBytes);

//            String cryptString = parseByte2HexStr(outputBytes);
            String cryptString = new String(encode);
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputFile));
            bufferedWriter.write(cryptString);
            bufferedWriter.flush();
            bufferedWriter.close();
//            inputStream.close();
        } catch (Exception ex) {
            throw new Exception("Error encrypting/decrypting file", ex);
        }
    }

    public static void decrypt(String key, File inputFile, File outputFile) throws Exception {
//        doCrypto(Cipher.DECRYPT_MODE, key, inputFile, outputFile);
        try {
            Key secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            IvParameterSpec iv = new IvParameterSpec(key.getBytes());

            cipher.init(Cipher.DECRYPT_MODE, secretKey,iv);

            FileInputStream inputStream = new FileInputStream(inputFile);
            byte[] inputBytes = new byte[(int) inputFile.length()];
            inputStream.read(inputBytes);
//            byte[] outputBytes = cipher.doFinal(parseHexStr2Byte(new String(inputBytes)));
            byte[] outputBytes = cipher.doFinal(Base64.getDecoder().decode(inputBytes));
            String textString = new String(outputBytes);
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputFile));
            bufferedWriter.write(textString);
            bufferedWriter.flush();
            bufferedWriter.close();
            inputStream.close();

        } catch (Exception ex) {
            throw new Exception("Error encrypting/decrypting file", ex);
        }
    }

    private static void doCrypto(int cipherMode, String key, File inputFile, File outputFile) throws Exception {
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
            throw new Exception("Error encrypting/decrypting file", ex);
        }
    }

    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length()/2];
        for (int i = 0;i< hexStr.length()/2; i++) {
            int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    public static final String keyCommon = "ymepub3554700623";
    public static void main(String[] args) throws Exception{

        File file = new File("a/b/c/d/");
        System.out.println();

        String sourceFile = "/Users/dingpengwei/Downloads/OEBPS/Text/ds00588401.xhtml";
        String encryptFile = "/Users/dingpengwei/Downloads/OEBPS/Text/ds00588401_en.xhtml";
        String decryptFile = "/Users/dingpengwei/Downloads/OEBPS/Text/ds00588401_de.xhtml";
        File inputFile = new File(sourceFile);
        File encryptedFile = new File(encryptFile);
        File decryptedFile = new File(decryptFile);
        CryptoUtils cryptoUtils = new CryptoUtils();
        try {
            cryptoUtils.encrypt(keyCommon, inputFile, encryptedFile);
            cryptoUtils.decrypt(keyCommon, encryptedFile, decryptedFile);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }
}
