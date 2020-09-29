package chen.wind;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Scanner;

public class ReversiClient {

    public void start() throws Exception{
        String hostname="127.0.0.1";
        final int port=8002;
        Selector selector=Selector.open();
        SocketChannel socketChannel=SocketChannel.
        		open(new InetSocketAddress(hostname, port));
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
        System.out.println("Successful started");
        new Thread(new ReversiClientHandler(selector)).start();
        Scanner scanner= new Scanner(System.in);
        while(scanner.hasNextLine()) {
        	String request=scanner.nextLine();
        	if(request!=null && request.length()>0) {
        		socketChannel.write(
        				Charset.forName("UTF-8")
        				.encode(request));
        	}
        }
    }
    
    
    public static void main(String[] args) throws Exception {
        new ReversiClient().start();
    }
}
