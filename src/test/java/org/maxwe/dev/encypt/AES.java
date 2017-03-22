package org.maxwe.dev.encypt;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Created by Pengwei Ding on 2016-06-02 10:16.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class AES {
    private static final String KEY_ALGORITHM = "AES";
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";
    private static final String DEFAULT_CIPHER_ALGORITHM2 = "AES/ECB/Padding";

    public static byte[] initSecretKey() {
        //返回生成指定算法的秘密密钥的 KeyGenerator 对象
        KeyGenerator kg = null;
        try {
            kg = KeyGenerator.getInstance(KEY_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return new byte[0];
        }
        //初始化此密钥生成器，使其具有确定的密钥大小
        //AES 要求密钥长度为 128
        kg.init(128);
        //生成一个密钥
        SecretKey secretKey = kg.generateKey();
        return secretKey.getEncoded();
    }

    private static Key toKey(byte[] key) {
        //生成密钥
        return new SecretKeySpec(key, KEY_ALGORITHM);
    }


    public static byte[] encrypt(byte[] data, Key key) throws Exception {
        return encrypt(data, key, DEFAULT_CIPHER_ALGORITHM);
    }


    public static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        return encrypt(data, key, DEFAULT_CIPHER_ALGORITHM);
    }

    public static byte[] encrypt(byte[] data, byte[] key, String cipherAlgorithm) throws Exception {
        //还原密钥
        Key k = toKey(key);
        return encrypt(data, k, cipherAlgorithm);
    }


    public static byte[] encrypt(byte[] data, Key key, String cipherAlgorithm) throws Exception {
        //实例化
        Cipher cipher = Cipher.getInstance(cipherAlgorithm);
        //使用密钥初始化，设置为加密模式
        cipher.init(Cipher.ENCRYPT_MODE, key);
        //执行操作
        return cipher.doFinal(data);
    }

    public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        return decrypt(data, key, DEFAULT_CIPHER_ALGORITHM);
    }


    public static byte[] decrypt(byte[] data, Key key) throws Exception {
        return decrypt(data, key, DEFAULT_CIPHER_ALGORITHM);
    }


    public static byte[] decrypt(byte[] data, byte[] key, String cipherAlgorithm) throws Exception {
        //还原密钥
        Key k = toKey(key);
        return decrypt(data, k, cipherAlgorithm);
    }

    public static byte[] decrypt(byte[] data, Key key, String cipherAlgorithm) throws Exception {
        //实例化
        Cipher cipher = Cipher.getInstance(cipherAlgorithm);
        //使用密钥初始化，设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, key);
        //执行操作
        return cipher.doFinal(data);
    }

    private static String showByteArray(byte[] data) {
        if (null == data) {
            return null;
        }
        StringBuilder sb = new StringBuilder("{");
        for (byte b : data) {
            sb.append(b).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("}");
        return sb.toString();
    }


    /**
     * 将二进制转换成16进制
     *
     * @param buf
     * @return
     */
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

    /**
     * 将16进制转换为二进制
     *
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }


    public static void main(String[] args) throws Exception {
//        byte[] key = initSecretKey();
//        System.out.println("key：" + showByteArray(key));
//        Key k = toKey(key);
//        String data = "AES数据";
//        System.out.println("加密前数据: string:" + data);
//        System.out.println("加密前数据: byte[]:" + showByteArray(data.getBytes()));
//        System.out.println();
//        byte[] encryptData = encrypt(data.getBytes(), k);
//        System.out.println("加密后数据: byte[]:" + showByteArray(encryptData));
//        System.out.println("加密后数据: hexStr:" + parseByte2HexStr(encryptData));
//        System.out.println();
//        byte[] decryptData = decrypt(encryptData, k);
//        System.out.println("解密后数据: byte[]:" + showByteArray(decryptData));
//        System.out.println("解密后数据: string:" + new String(decryptData));


        AES aes = new AES();
        String sourceFile = "/Users/dingpengwei/Downloads/xxx/OEBPS/Text/Section0008.htm";
        String encryptFile = "/Users/dingpengwei/Downloads/xxx/OEBPS/Text/Section0008_en.htm";
        String decryptFile = "/Users/dingpengwei/Downloads/xxx/OEBPS/Text/Section0008_de.htm";

        try {
            String keys = "ymepub355470062381849";
            aes.AESFileEncryption(keys, sourceFile, encryptFile);
            aes.AESFileDecryption(keys, encryptFile, decryptFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        aes.encryptFile(new File(sourceFile), encryptFile, keys);
//        aes.decryptFile(new File(encryptFile), decryptFile, keys);

    }


    /**
     * 初始化 AES Cipher
     *
     * @param sKey
     * @param cipherMode
     * @return
     */
    public static Cipher initAESCipher(String sKey, int cipherMode) throws Exception {

        //返回生成指定算法的秘密密钥的 KeyGenerator 对象
        KeyGenerator kg = null;
        try {
            kg = KeyGenerator.getInstance(KEY_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        //初始化此密钥生成器，使其具有确定的密钥大小
        //AES 要求密钥长度为 128
        kg.init(128);
        //生成一个密钥
        SecretKey secretKey = kg.generateKey();
        byte[] encoded = secretKey.getEncoded();
        SecretKeySpec secretKeySpec = new SecretKeySpec(encoded, KEY_ALGORITHM);

        Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM2);
        //使用密钥初始化，设置为加密模式
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
//
//        //创建Key gen
//        KeyGenerator keyGenerator = null;
//        Cipher cipher = null;
//        try {
//            keyGenerator = KeyGenerator.getInstance("AES");
//            keyGenerator.init(128, new SecureRandom(sKey.getBytes()));
//            SecretKey secretKey = keyGenerator.generateKey();
//            byte[] codeFormat = secretKey.getEncoded();
//            SecretKeySpec key = new SecretKeySpec(codeFormat, "AES");
//            cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
//            cipher.init(cipherMode, key);
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        } catch (NoSuchPaddingException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        } catch (InvalidKeyException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
        return cipher;
    }

    /**
     * 对文件进行AES加密
     *
     * @param sourceFile
     * @param encryptFile
     * @param sKey
     * @return
     */
    public void encryptFile(File sourceFile, String encryptFile, String sKey) throws Exception {
        //新建临时加密文件
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = new FileInputStream(sourceFile);
            outputStream = new FileOutputStream(new File(encryptFile));
            Cipher cipher = initAESCipher(sKey, Cipher.ENCRYPT_MODE);
            //以加密流写入文件
            CipherInputStream cipherInputStream = new CipherInputStream(inputStream, cipher);
            byte[] cache = new byte[1024];
            int nRead = 0;
            while ((nRead = cipherInputStream.read(cache)) != -1) {
                outputStream.write(cache, 0, nRead);
                outputStream.flush();
            }
            cipherInputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }

    /**
     * AES方式解密文件
     *
     * @param sourceFile
     * @return
     */
    public void decryptFile(File sourceFile, String decryptFile, String sKey) throws Exception {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            Cipher cipher = initAESCipher(sKey, Cipher.DECRYPT_MODE);
            inputStream = new FileInputStream(sourceFile);
            outputStream = new FileOutputStream(decryptFile);
            CipherOutputStream cipherOutputStream = new CipherOutputStream(outputStream, cipher);
            byte[] buffer = new byte[1024];
            int r;
            while ((r = inputStream.read(buffer)) >= 0) {
                cipherOutputStream.write(buffer, 0, r);
            }
            cipherOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }

    private static final int IV_LENGTH = 16;

    public static void AESFileEncryption(String password, String sourceFile, String encryptionFile) throws Exception {
        InputStream inputStream = new FileInputStream(sourceFile);
        OutputStream outputStream = new FileOutputStream(encryptionFile);
        SecureRandom r = new SecureRandom();
        byte[] iv = new byte[password.length()];
        r.nextBytes(iv);
        outputStream.write(iv);
        outputStream.flush();
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding"); //"DES/ECB/PKCS5Padding";"AES/CBC/PKCS5Padding"//"AES/CFB8/NoPadding"
        SecretKeySpec keySpec = new SecretKeySpec(password.getBytes(), "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);

        CipherOutputStream cipherOutputStream = new CipherOutputStream(outputStream, cipher);
        byte[] buf = new byte[1024];
        int numRead = 0;
        while ((numRead = inputStream.read(buf)) >= 0) {
            cipherOutputStream.write(buf, 0, numRead);
        }
        cipherOutputStream.close();
    }

    public static void AESFileDecryption(String password,String encryptionFile ,String decryptionFile) throws Exception{
        InputStream inputStream = new FileInputStream(encryptionFile);
        OutputStream outputStream = new FileOutputStream(decryptionFile);
        byte[] iv = new byte[password.length()];
        inputStream.read(iv);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding"); //"DES/ECB/PKCS5Padding";"AES/CBC/PKCS5Padding",//"AES/CFB8/NoPadding"
        SecretKeySpec keySpec = new SecretKeySpec(password.getBytes(), "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

        CipherInputStream cipherInputStream = new CipherInputStream(inputStream, cipher);
        byte[] buf = new byte[1024];
        int numRead = 0;
        while ((numRead = cipherInputStream.read(buf)) >= 0) {
            outputStream.write(buf, 0, numRead);
        }
        outputStream.close();
    }
}
