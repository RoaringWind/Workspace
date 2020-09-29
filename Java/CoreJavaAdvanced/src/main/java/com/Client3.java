/**
 * 
 */
package com;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author blood
 *
 */
public class Client3 {

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        Socket socket=new Socket("127.0.0.1", 8001);
        InputStream inputStream=socket.getInputStream();
        OutputStream outputStream=socket.getOutputStream();
        outputStream.write("hello.html".getBytes());
        byte[] b=new byte[1024];
        inputStream.read(b);
        File file=new File("src/main/resources/hello.html");
        file.createNewFile();
        OutputStream fileOutputStream=new FileOutputStream(file);
        fileOutputStream.write(b);
        fileOutputStream.close();
    }

}
