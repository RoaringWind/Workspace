package com;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class Client1 {

    public static void main(String[] args) throws Exception {
        int port=8001;
        Socket socket=new Socket("127.0.0.1", port);
        InputStream inputStream=socket.getInputStream();
        OutputStream outputStream=socket.getOutputStream();
        outputStream.write("hello.html".getBytes());
        
    }

}
