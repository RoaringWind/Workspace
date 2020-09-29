package com;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.util.Scanner;

public class Server2 {

    public static void main(String[] args) {
        while(true) {
            try(var s=new ServerSocket(8001)){
                var socket=s.accept();
                InputStream inputStream=socket.getInputStream();
                OutputStream outputStream=socket.getOutputStream();
                System.out.println(System.getProperty("user.dir"));
                File serverFile=new File("D:\\Workspace\\Java\\CoreJavaAdvanced\\src\\main\\resources\\hello-server.html");
                Scanner scanner=new Scanner(serverFile);
                String resultString="";
                while(scanner.hasNextLine()) {
                    resultString+=scanner.nextLine();
                }
                System.out.println("String is "+resultString);
                outputStream.write(resultString.getBytes());
                break;
            }catch (Exception e) {
                // TODO: handle exception
            }
        }

    }

}
