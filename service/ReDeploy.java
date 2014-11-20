import org.vertx.java.core.Handler;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.platform.Verticle;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by dingpengwei on 11/14/18.
 */
public class ReDeploy extends Verticle {

    @Override
    public void start() {
        vertx.createHttpServer().requestHandler(new Handler<HttpServerRequest>() {
            public void handle(HttpServerRequest req) {
                String repo = req.path().replaceFirst("/","");
                System.out.println(req.query());
                if("home".equals(repo)){
                    System.out.println("home");
                    String[] cmds = new String[]{"/bin/sh","-c","cd /usr/local/bin/home/;git pull"};
                    cmd(cmds);
                }
                if("mod-im".equals(repo)){
                    System.out.println("mod-im");
//                    String[] cmds = new String[]{"/bin/sh","-c","cd /usr/local/bin/home/;git pull"};
//                    cmd(cmds);
                }
                req.response().end();
            }
        }).listen(1988);
    }

    private void cmd(String[] cmds){
        InputStream in = null;
        try {
            Process pro = Runtime.getRuntime().exec(cmds);
            pro.waitFor();
            in = pro.getInputStream();
            BufferedReader read = new BufferedReader(new InputStreamReader(in));
            String result = read.readLine();
            System.out.println("cmd result : " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}