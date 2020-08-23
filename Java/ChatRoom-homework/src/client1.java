import java.net.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class client1 {

    public static void main(String[] args) throws Exception {
        try (Socket socket = new Socket("127.0.0.1", 8001);
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream())) {
             
            ClientMessageReceiver messageReceiver = new ClientMessageReceiver(dis);
            new Thread(messageReceiver).start();
            int i=0;
            String input = null;
            do {
                input =socket.getLocalPort()+":"+i+++"";
                dos.writeUTF(input);
                Thread.sleep(5000);
            } while (input!=null);
            messageReceiver.stop();
        } catch (IOException e) {
            e.printStackTrace();
        }
 
    }
}