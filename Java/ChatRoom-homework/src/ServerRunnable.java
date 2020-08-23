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
                System.out.println("Port "+id+"said: "+string);//����ÿ���ͻ��˵�����Ϣ
                for (Entry<Integer, Socket> entry:server.map.entrySet()) {
                    if(id!=entry.getKey()) {
                        new DataOutputStream(entry.getValue().getOutputStream()).writeUTF(string);//ת��������ͻ���
                    }
                }
            }catch (Exception e) {
                // TODO: handle exception
            }
        }
    }
    
}
