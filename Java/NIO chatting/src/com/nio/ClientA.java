package com.nio;

import java.io.IOException;
import java.util.Random;
 
public class ClientA {
 
    public static void main(String[] args)
            throws IOException {
        String filename=getRandomString(10);
        new NioClient().start(filename);
    }
    public static String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
          int number=random.nextInt(62);
          sb.append(str.charAt(number));
        }
        return sb.toString();
    }
 
}