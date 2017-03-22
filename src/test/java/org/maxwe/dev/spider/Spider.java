package org.maxwe.dev.spider;


import javax.net.ssl.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2016-07-29 15:50.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class Spider {

    public static void main(String[] args) throws Exception {
//        LinkedList<Item> byString = getByString("http://www.haaic.gov.cn/module/col/col6874/index.jsp");

        postData("hah");

    }

    private static boolean postData(String name) throws Exception {
        String url = "Https://crm.alibaba-inc.com/noah/platform/customer/.rpc2RVmjKq-vFK1lUn7-08MwpINT6uvboV.ErJRw6FvkqonIJgRELiEKSzYjIkX0rpDnkawjF8-Q0.1xs6fKADWXS8NkB7a8PF51yy7NH0njr9t187XHkiN1vKK29m03PSHhs2TDGoGp7Q53vnfUGrYVpIXOhNiEL7MlwqjO6fx7Rdxb97B0NIq.do";
        String _id_ = "FoqIkw";
        String _args_ = "[{\"$rid\":\"\",\"phoneCountryCode\":\"86\",\"faxCountryCode\":\"86\",\"mobileCountryCode\":\"86\",\"depotLeadsSource2\":\"96025\",\"type\":\"enterprise\",\"country\":\"CN\",\"productLine\":\"1\",\"companyName\":\""+name+"\",\"corporateRepresent\":\"祁云鹏\",\"phoneAreaCode\":\"\",\"phoneNumber\":\"1234567\",\"mobilePhoneNumber\":\"\",\"faxAreaCode\":null,\"faxPhoneNumber\":\"\",\"email\":null,\"select_countryField_znxeE\":\"CN\",\"distType\":\"owner\",\"distTarget\":\"wb-qyp193954\"}]";
        String t__ = "0.35861875815317035";

        URL myurl = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection)myurl.openConnection();
        con.setRequestMethod("POST");

        String value = "hello";

        con.setRequestProperty("Content-length", _args_.length() + "");
        con.setRequestProperty("Content-Language", "zh-CN");
        con.setRequestProperty("shy-rt", "json");
        con.setRequestProperty("Expires", "-1");
        con.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
        con.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0;Windows98;DigExt)");

        con.setDoOutput(true);
        con.setDoInput(true);

        DataOutputStream output = new DataOutputStream(con.getOutputStream());

        output.writeBytes(value);

        output.close();
        DataInputStream input = new DataInputStream( con.getInputStream() );
        for( int c = input.read(); c != -1; c = input.read() ){
            System.out.print( (char)c );
        }
        input.close();
        System.out.println("Resp Code:"+con .getResponseCode());
        System.out.println("Resp Message:"+ con .getResponseMessage());



        return false;
    }

    /**
     * 测试方法.
     * @param args
     * @throws Exception
     */
    public static void main2(String[] args) throws Exception {
        // 密码
        String password = "123456";
        // 密钥库
        String keyStorePath = "tomcat.keystore";
        // 信任库
        String trustStorePath = "tomcat.keystore";
        // 本地起的https服务
        String httpsUrl = "https://localhost:8443/service/httpsPost";
        // 传输文本
        String xmlStr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><fruitShop><fruits><fruit><kind>萝卜</kind></fruit><fruit><kind>菠萝</kind></fruit></fruits></fruitShop>";
        initHttpsURLConnection(password, keyStorePath, trustStorePath);
        // 发起请求
        post(httpsUrl, xmlStr);
    }




    /**
     * 发送请求.
     * @param httpsUrl
     *            请求的地址
     * @param xmlStr
     *            请求的数据
     */
    public static void post(String httpsUrl, String xmlStr) {
        HttpsURLConnection urlCon = null;
        try {
            urlCon = (HttpsURLConnection) (new URL(httpsUrl)).openConnection();
            urlCon.setDoInput(true);
            urlCon.setDoOutput(true);
            urlCon.setRequestMethod("POST");
            urlCon.setRequestProperty("Content-Length",
                    String.valueOf(xmlStr.getBytes().length));
            urlCon.setUseCaches(false);
            //设置为gbk可以解决服务器接收时读取的数据中文乱码问题
            urlCon.getOutputStream().write(xmlStr.getBytes("gbk"));
            urlCon.getOutputStream().flush();
            urlCon.getOutputStream().close();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    urlCon.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 初始化HttpsURLConnection.
     * @param password
     *            密码
     * @param keyStorePath
     *            密钥库路径
     * @param trustStorePath
     *            信任库路径
     * @throws Exception
     */
    public static void initHttpsURLConnection(String password,
                                              String keyStorePath, String trustStorePath) throws Exception {
        // 声明SSL上下文
        SSLContext sslContext = null;
        // 实例化主机名验证接口
        HostnameVerifier hnv = new MyHostnameVerifier();
        try {
            sslContext = getSSLContext(password, keyStorePath, trustStorePath);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        if (sslContext != null) {
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
        }
        HttpsURLConnection.setDefaultHostnameVerifier(hnv);
    }


    /**
     * 获得SSLSocketFactory.
     * @param password
     *            密码
     * @param keyStorePath
     *            密钥库路径
     * @param trustStorePath
     *            信任库路径
     * @return SSLSocketFactory
     * @throws Exception
     */
    public static SSLContext getSSLContext(String password,
                                           String keyStorePath, String trustStorePath) throws Exception {
        // 实例化密钥库
        KeyManagerFactory keyManagerFactory = KeyManagerFactory
                .getInstance(KeyManagerFactory.getDefaultAlgorithm());
        // 获得密钥库
        KeyStore keyStore = getKeyStore(password, keyStorePath);
        // 初始化密钥工厂
        keyManagerFactory.init(keyStore, password.toCharArray());

        // 实例化信任库
        TrustManagerFactory trustManagerFactory = TrustManagerFactory
                .getInstance(TrustManagerFactory.getDefaultAlgorithm());
        // 获得信任库
        KeyStore trustStore = getKeyStore(password, trustStorePath);
        // 初始化信任库
        trustManagerFactory.init(trustStore);
        // 实例化SSL上下文
        SSLContext ctx = SSLContext.getInstance("TLS");
        // 初始化SSL上下文
        ctx.init(keyManagerFactory.getKeyManagers(),
                trustManagerFactory.getTrustManagers(), null);
        // 获得SSLSocketFactory
        return ctx;
    }


    /**
     * 获得KeyStore.
     * @param keyStorePath
     *            密钥库路径
     * @param password
     *            密码
     * @return 密钥库
     * @throws Exception
     */
    public static KeyStore getKeyStore(String password, String keyStorePath) throws Exception {
        // 实例化密钥库
        KeyStore ks = KeyStore.getInstance("JKS");
        // 获得密钥库文件流
        FileInputStream is = new FileInputStream(keyStorePath);
        // 加载密钥库
        ks.load(is, password.toCharArray());
        // 关闭密钥库文件流
        is.close();
        return ks;
    }

    public static class MyHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            if("localhost".equals(hostname)){
                return true;
            } else {
                return false;
            }
        }
    }



    private static class Item {
        private String name;
        private String time;

        public Item() {
        }

        public Item(String name, String time) {
            this.name = name;
            this.time = time;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }

    private final static LinkedList<Item> getByString(String urlString) throws Exception {
        LinkedList<Item> items = new LinkedList<Item>();
        try {
            URL url = new URL(urlString);
            URLConnection urlConnection = url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            int index = -1;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.equals("<script language=javascript>")) {
                    index = 0;
                }
                if (line.equals("</script>")) {
                    index = 1;
                }
                if (index == 0) {
                    if (line.startsWith("\theaders[i]=")) {
                        Item item = new Item();
                        item.setName(line.split("=")[1].replace("'", "").replace(";", ""));
                        bufferedReader.readLine();
                        line = bufferedReader.readLine();
                        item.setTime(line.split("=")[1].replace("'", "").replace(";", ""));
                        items.add(item);
                    }
                }
            }
            bufferedReader.close();
            inputStream.close();
            System.out.println("共获取数据量: " + items.size());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return items;
    }
}
