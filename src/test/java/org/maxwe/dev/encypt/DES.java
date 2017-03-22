package org.maxwe.dev.encypt;

import javax.crypto.*;
import java.io.*;
import java.security.SecureRandom;

/**
 * Created by Pengwei Ding on 2016-03-11 13:56.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class DES {
    private static String path1 = DES.class.getResource("/").getPath() + "ds00216101.xhtml";
    private static String path2 = DES.class.getResource("/").getPath() + "ds00216101_2.xhtml";
    private static String path3 = DES.class.getResource("/").getPath() + "ds00216101_3.xhtml";
    private static final String ALGORITHM = "DES";
    private static String KEY_FILE;

    public static void main(String[] args) throws Exception {
        if (args == null || args.length < 1 || args[0] == null){
            throw new Exception("请输入密钥文件地址");
        }
        KEY_FILE = args[0];
        DES test = new DES();
        File file = new File(KEY_FILE);
        if (!file.exists()){
            test.keyGenerator("");
        }
        test.encrypt(path1,path2);
        test.decrypt(path2, path3);
        test.myDecrypt(path2);
    }

    public void keyGenerator(String key) {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
            keyGenerator.init(new SecureRandom(key.getBytes()));
            SecretKey secretKey = keyGenerator.generateKey();
            FileOutputStream fileOutputStream = new FileOutputStream(KEY_FILE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(secretKey);
            fileOutputStream.flush();
            objectOutputStream.flush();
            fileOutputStream.close();
            objectOutputStream.close();
        } catch (Exception e) {
            throw new RuntimeException("Error initializing SqlMap class. Cause: " + e);
        }
    }

    /**
     * 文件file进行加密并保存目标文件destFile中
     *
     * @param file     要加密的文件 如c:/test/srcFile.txt
     * @param destFile 加密后存放的文件名 如c:/加密后文件.txt
     */
    public void encrypt(String file, String destFile) throws Exception {
        long time1 = System.currentTimeMillis();
        FileInputStream fileInputStream = new FileInputStream(KEY_FILE);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        SecretKey secretKey = (SecretKey) objectInputStream.readObject();
        fileInputStream.close();
        objectInputStream.close();
        long time2 = System.currentTimeMillis();
        System.out.println("加密读取密钥耗时：" + (time2 - time1));
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        InputStream inputStream = new FileInputStream(file);
        OutputStream outputStream = new FileOutputStream(destFile);
        CipherInputStream cipherInputStream = new CipherInputStream(inputStream, cipher);
        byte[] buffer = new byte[1024];
        int r;
        while ((r = cipherInputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, r);
        }
        inputStream.close();
        cipherInputStream.close();
        outputStream.close();
        long time3 = System.currentTimeMillis();
        System.out.println("加密完成耗时：" + (time3 - time2));
    }

    /**
     * 文件采用DES算法解密文件
     *
     * @param file 已加密的文件 如c:/加密后文件.txt
     *             * @param destFile
     *             解密后存放的文件名 如c:/ test/解密后文件.txt
     */
    public void decrypt(String file, String dest) throws Exception {
        long time1 = System.currentTimeMillis();
        FileInputStream fileInputStream = new FileInputStream(KEY_FILE);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        SecretKey secretKey = (SecretKey) objectInputStream.readObject();
        fileInputStream.close();
        objectInputStream.close();
        long time2 = System.currentTimeMillis();
        System.out.println("加密读取密钥耗时：" + (time2 - time1));
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        InputStream inputStream = new FileInputStream(file);
        OutputStream outputStream = new FileOutputStream(dest);
        CipherOutputStream cipherOutputStream = new CipherOutputStream(outputStream, cipher);
        byte[] buffer = new byte[1024];
        int r;
        while ((r = inputStream.read(buffer)) >= 0) {
            cipherOutputStream.write(buffer, 0, r);
        }
        cipherOutputStream.close();
        outputStream.close();
        inputStream.close();
        long time3 = System.currentTimeMillis();
        System.out.println("加密完成耗时：" + (time3 - time2));
    }

    public void myDecrypt(String file) throws Exception {
        long time1 = System.currentTimeMillis();
        FileInputStream fileInputStream = new FileInputStream(KEY_FILE);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        SecretKey secretKey = (SecretKey) objectInputStream.readObject();
        fileInputStream.close();
        objectInputStream.close();
        long time2 = System.currentTimeMillis();
        System.out.println("加密读取密钥耗时：" + (time2 - time1));
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        InputStream inputStream = new FileInputStream(file);
        int available = inputStream.available();
        byte[] buffer = new byte[available];
        inputStream.read(buffer);
        byte[] bytes = cipher.doFinal(buffer);

        inputStream.close();
        String string = new String(bytes);
        System.out.println(string);
        long time3 = System.currentTimeMillis();
        System.out.println("加密完成耗时：" + (time3 - time2));

    }
}
