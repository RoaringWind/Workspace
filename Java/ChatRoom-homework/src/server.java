import java.net.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class server {

    static int serverPort=8001;
    static Map<Integer, Socket> map=new HashMap<Integer,Socket>();
    
    public static void main(String[] args) {
        try (ServerSocket ss = new ServerSocket(serverPort)) {
            System.out.println("�����ҷ������������������ڼ���" + serverPort + "�˿�");
            while (true) {
                try {
                    Socket socket = ss.accept();
                    System.out.println("�����û����ӵ��������ˣ���ϢΪ��" + socket);
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
