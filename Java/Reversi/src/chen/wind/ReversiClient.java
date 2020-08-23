package chen.wind;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Scanner;
public class ReversiClient {

    public static void main(String[] args) throws Exception {
        new ReversiClient().start();
    }
    public static void start() throws Exception{
        String hostname="127.0.0.1";
        int port=8001;
        Selector selector=null;
        SocketChannel socketChannel=null;
        try {
            selector=Selector.open();
            socketChannel=SocketChannel.open();
            socketChannel.configureBlocking(false);
            if(socketChannel.connect(new InetSocketAddress(hostname, port))) {
                socketChannel.register(selector, SelectionKey.OP_READ);
                respondToServer(socketChannel);
            }else {
                socketChannel.register(selector, SelectionKey.OP_CONNECT);
            }
        } catch (Exception e) {
            
        }
    }
    public static void respondToServer(SocketChannel socketChannel) throws IOException {
        Scanner scanner=new Scanner(System.in);
        String inputString="";
//        while(inputString.length()==3 && inputString.charAt(0))
        inputString=scanner.nextLine();
        socketChannel.write(Charset.forName("UTF-8").encode(inputString));
        
    }
    public static void handleInputFromServer(Selector selector,SelectionKey key)throws Exception{
        if(key.isValid()) {
            SocketChannel sc=(SocketChannel )key.channel();
            if(key.isConnectable()){
                if(sc.finishConnect()) {
                    sc.register(selector, SelectionKey.OP_READ);
                }
            }
            if(key.isReadable()) {
                
            }
        }
        
    }
}
