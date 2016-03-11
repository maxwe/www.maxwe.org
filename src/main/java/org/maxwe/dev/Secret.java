package org.maxwe.dev;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import java.io.*;

/**
 * Created by Pengwei Ding on 2016-03-11 13:56.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class Secret {
    private static final String ignoreDir = "ignore/md";
    private static final String projectDir = "project/md";
    private static final String testProjectDir = "/Users/dingpengwei/Desktop/project/md";

    private static final String ALGORITHM = "DES";
    private static String KEY_FILE;
    public static void main(String[] args) throws Exception {
        if (args == null || args.length < 1 || args[0] == null){
            throw new Exception("请输入密钥文件地址");
        }
        KEY_FILE = args[0];
        Secret secret = new Secret();
        SecretKey secretKey = secret.readKey();
        File[] ignoreFiles = new File(ignoreDir).listFiles();
        for (File file:ignoreFiles){
            secret.encrypt(secretKey,file.getAbsolutePath(),projectDir + File.separator + file.getName());
        }

        File[] projectFiles = new File(projectDir).listFiles();
        for (File file:projectFiles){
            secret.decrypt(secretKey, file.getAbsolutePath(), testProjectDir + File.separator + file.getName());
            secret.decryptToBuffer(secretKey,file.getAbsolutePath());
        }
    }

    private SecretKey readKey() throws Exception {
        FileInputStream fileInputStream = new FileInputStream(KEY_FILE);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        SecretKey secretKey = (SecretKey) objectInputStream.readObject();
        fileInputStream.close();
        objectInputStream.close();
        return secretKey;
    }

    public void encrypt(SecretKey secretKey,String file, String destFile) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        InputStream inputStream = new FileInputStream(file);
        OutputStream outputStream = new FileOutputStream(destFile);
        CipherInputStream cipherInputStream = new CipherInputStream(inputStream, cipher);
        byte[] buffer = new byte[1024 * 1024];
        int r;
        while ((r = cipherInputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, r);
        }
        inputStream.close();
        cipherInputStream.close();
        outputStream.close();
    }

    public void decrypt(SecretKey secretKey,String file, String dest) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        InputStream inputStream = new FileInputStream(file);
        OutputStream outputStream = new FileOutputStream(dest);
        CipherOutputStream cipherOutputStream = new CipherOutputStream(outputStream, cipher);
        byte[] buffer = new byte[1024 * 1024];
        int r;
        while ((r = inputStream.read(buffer)) >= 0) {
            cipherOutputStream.write(buffer, 0, r);
        }
        cipherOutputStream.close();
        outputStream.close();
        inputStream.close();
    }

    public void decryptToBuffer(SecretKey secretKey,String file) throws Exception {
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
    }
}
