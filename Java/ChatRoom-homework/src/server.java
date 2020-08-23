import java.net.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class server {

    static int serverPort=8001;
    static Map<Integer, Socket> map=new HashMap<Integer,Socket>();
    
    public static void main(String[] args) {
        try (ServerSocket ss = new ServerSocket(serverPort)) {
            System.out.println("聊天室服务器端已启动，正在监听" + serverPort + "端口");
            while (true) {
                try {
                    Socket socket = ss.accept();
                    System.out.println("有新用户连接到服务器端，信息为：" + socket);
                    new Thread(new ServerRunnable(socket)).start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
 
    }
}
