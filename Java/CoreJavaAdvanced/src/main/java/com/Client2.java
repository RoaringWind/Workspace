package com;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client2 {

    public static void main(String[] args) throws Exception{
        // TODO Auto-generated method stub
        Socket socket=new Socket("127.0.0.1", 8001);
        System.out.println("成功链接服务器");
        InputStream inputStream=socket.getInputStream();
        OutputStream outputStream=socket.getOutputStream();
        byte[] bytes =new byte[1024];
        inputStream.read(bytes);
        String string=new String(bytes);
        System.out.println(string);
        File file=new File("D:\\Workspace\\Java\\CoreJavaAdvanced\\src\\main\\resources\\hello.html");
        file.createNewFile();
        InputStream fileInputStream=new FileInputStream(file);
        OutputStream fileOutputStream=new FileOutputStream(file);
        fileOutputStream.write(bytes);
        fileOutputStream.close();
        fileInputStream.close();
    }
}
