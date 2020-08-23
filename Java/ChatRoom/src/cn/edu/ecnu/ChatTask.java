package cn.edu.ecnu;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.Callable;


public class ChatTask implements Runnable{
    private String serverString;
    private int clientPort;
    private String words;
    private Socket socket;
    public ChatTask(Socket socket,String serverString, int port,String words) {
        this.serverString=serverString;
        this.clientPort=port;
        this.words=words;
        this.socket=socket;
    }
    
    @Override
    public void run() {
        try{
            InputStream ipStream=this.socket.getInputStream();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(ipStream));
            OutputStream opStream=this.socket.getOutputStream();
            DataOutputStream dataOutputStream=new DataOutputStream(opStream);
            BufferedReader bReader=new BufferedReader(new InputStreamReader(ipStream));
            dataOutputStream.writeBytes("Port :"+this.socket.getLocalPort()+" "+this.words+System.getProperty("line.separator"));
        }catch (Exception e) {
            e.printStackTrace();
        }
        
    }

}
