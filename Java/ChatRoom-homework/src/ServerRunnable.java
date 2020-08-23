import java.io.*;
import java.net.*;
import java.util.Map.Entry;

public class ServerRunnable implements Runnable{
    private DataInputStream dips;
    private DataOutputStream dops;
    private Socket socket;
    private int id;
    public ServerRunnable(Socket socket) throws IOException{
        this.socket=socket;
        this.dips=new DataInputStream(socket.getInputStream());
        this.dops=new DataOutputStream(socket.getOutputStream());
        
    }
    @Override
    public void run() {
        id=socket.getPort();
        server.map.put(id,socket);
        while(true) {
            int i=0;
            try {
                String string=dips.readUTF();
                System.out.println("Port "+id+"said: "+string);//接受每个客户端的新信息
                for (Entry<Integer, Socket> entry:server.map.entrySet()) {
                    if(id!=entry.getKey()) {
                        new DataOutputStream(entry.getValue().getOutputStream()).writeUTF(string);//转发给其余客户端
                    }
                }
            }catch (Exception e) {
                // TODO: handle exception
            }
        }
    }
    
}
