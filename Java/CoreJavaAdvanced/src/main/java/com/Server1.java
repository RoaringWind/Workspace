package com;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server1 {

    public static void main(String[] args) {
        while (true) {
            try (var s = new ServerSocket(8001)) {
                var socket = s.accept();
                InputStream inputStream = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream();
                outputStream.write("<head><body>hello world</body></html>".getBytes());
                byte[] inputBytes = new byte[1024];
                inputStream.read(inputBytes);
                System.out.println(new String(inputBytes));
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }

}
