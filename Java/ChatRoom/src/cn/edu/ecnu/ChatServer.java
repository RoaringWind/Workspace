package cn.edu.ecnu;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ForkJoinPool;

public class ChatServer {

    private static final int Number = 0;

    public static void main(String[] args) throws Exception{
        int serverPort=8001;
        //ServerSocket serverSocket=new ServerSocket(serverPort);
        //Socket socket=serverSocket.accept();
        System.out.println("Server Started");
        //opStream.write("Hello client".getBytes());
        ForkJoinPool pool=new ForkJoinPool(6);
        Map<Integer,Socket> hashMap=new HashMap<Integer,Socket>();
        ServerSocket ss=new ServerSocket(serverPort);
        //System.out.println(ss.accept());
        //System.out.println("Server waiting for Accepted!");
        Socket socket=ss.accept();
        //pool.submit();
        while (true) {
            try {
                //System.out.println("Server Accepted!");
                hashMap.put(socket.getLocalPort(), socket);
                InputStream ipStream=socket.getInputStream();//客户端的输出就是服务端的输入
                OutputStream opStream=socket.getOutputStream();//服务端的输出就是客户端的输入
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(ipStream));
                String receivedString=bufferedReader.readLine();
                System.out.println(receivedString);
                //System.out.printf("Received %s from %d\n", receivedString,socket.getLocalPort());
                for(Entry<Integer, Socket> entry:hashMap.entrySet()) {
                    if (entry.getKey()!=socket.getLocalPort()) {
                        System.out.printf("sending to port %d... \n",entry.getKey());
                        pool.submit(new ChatTask(entry.getValue(),"127.0.0.1",Number,receivedString));
                    }
                }
                //bufferedReader.notify();
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        }
    }
}

