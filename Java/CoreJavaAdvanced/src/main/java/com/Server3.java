package com;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Server3 {

    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor=(ThreadPoolExecutor) Executors.newFixedThreadPool(6);
        while (true) {
            try(var s=new ServerSocket(8001)){
                var ss=s.accept();
                InputStream inputStream=ss.getInputStream();
                OutputStream outputStream=ss.getOutputStream();
                inputStream.read();
                File file=new File("src/main/resources/hello-server.html");
                if(!file.exists()) {
                    System.out.println("Not exists");
                    break;
                }else {
                    InputStream inputStream2=new FileInputStream(file);
                    Task task=new Task(outputStream, inputStream2.readAllBytes());
                    threadPoolExecutor.execute(task);
                }
            }catch (Exception e) {
                
            }
        }
    }
}
class Task implements Runnable{
    private OutputStream outputStream=null;
    private byte[] bytes=null;
    public Task(OutputStream out,byte[] b) {
        this.outputStream=out;
        this.bytes=b;
    }
    @Override
    public void run(){
        try {
            System.out.println("Start writing");
            outputStream.write(bytes);
            System.out.println("End write");
        } catch (Exception e) {

        }
        
    }
    
}
