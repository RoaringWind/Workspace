package cn.edu.ecnu;

import java.io.*;
import java.net.*;
import java.util.*;

public class ChatClient1 {

    public static void main(String[] args) throws Exception{
        String address="127.0.0.1";
        int serverPort=8001;
        Socket socket=new Socket(address, serverPort);
        InputStream ipStream=socket.getInputStream();
        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(ipStream));
        OutputStream opStream=socket.getOutputStream();
        DataOutputStream dataOutputStream=new DataOutputStream(opStream);
        BufferedReader bReader=new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            String string=bReader.readLine();
            if (string.equalsIgnoreCase("quit!")) {
                break;
            }else {
                System.out.println("Client said:");
                dataOutputStream.writeBytes("Port :"+socket.getLocalPort()+" "+string+System.getProperty("line.separator"));
            }
            //System.out.println("the port is"+socket.getLocalPort());
        }
        dataOutputStream.close();
        bufferedReader.close();
        bReader.close();
        socket.close();
    }

}
