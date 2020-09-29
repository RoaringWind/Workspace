package reversi;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Scanner;



public class Client {
	public void start() throws Exception{
		SocketChannel socketChannel = SocketChannel.open(
                new InetSocketAddress("127.0.0.1", 8005));
		Selector selector = Selector.open();
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
        new Thread(new ClientHandler(selector)).start();
        Scanner scanner=new Scanner(System.in);
        while(scanner.hasNextLine()) {
        	String sendInfo=scanner.nextLine();
        	if(sendInfo!=null && sendInfo.length()>0) {
        		socketChannel.write(Charset.forName("UTF-8").encode(sendInfo));
        	}
        }
	}
	public static void main (String[] args) throws Exception {
		new Client().start();
	}
}
