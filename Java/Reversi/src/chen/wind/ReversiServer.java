package chen.wind;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class ReversiServer {

    public static void main(String[] args) throws Exception{
        new ReversiServer().start();
    }
    
    public void start() throws Exception{
        int serverPort=8002;
        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel=ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(serverPort));
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("服务器启动成功!");
        HashMap<SelectionKey, Integer> playerMap=new HashMap<SelectionKey, Integer>();
        int playerNo=1;
        for(;;) {
            int readyChannel=selector.select();
            if (readyChannel==0) {
            	continue;
            }
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> selectedIt=selectionKeys.iterator();
            Set<SelectionKey> ssSelectionKeys=selector.keys();
            System.out.println(ssSelectionKeys.size());
            if(ssSelectionKeys.size()!=2) {
	            while(selectedIt.hasNext()) {
	            	SelectionKey tmp=selectedIt.next();
	            	selectedIt.remove();
	            	if (tmp.isAcceptable()) {
	            		if(playerMap.getOrDefault(tmp, 0)==0) {
	                		playerMap.put(tmp, playerNo);
	                		playerNo*=-1;
	                		SocketChannel sc=serverSocketChannel.accept();
		            		sc.configureBlocking(false);
		            		sc.register(selector, SelectionKey.OP_READ);
	                	}
	                }
	            }
            }
            else{
            	System.out.println("now has 2 client");
            	Board board=new Board();
                for(;;) {
                	int readyChannels=selector.select();
                	if (readyChannels==0) continue;
                	Set<SelectionKey> selectedKeys=selector.selectedKeys();
                	Iterator<SelectionKey> it=selectedKeys.iterator();
                	while(it.hasNext()){
                		SelectionKey tmpKey=it.next();
                		System.out.println(tmpKey);
                		it.remove();
	                	if(tmpKey.isReadable()) {
	                		System.out.printf(tmpKey+"is readable\n");
	                		readHandler(tmpKey, selector, board, playerMap.get(tmpKey));
	                	}
	                	if (tmpKey.isAcceptable()) {
	                		SocketChannel sc=serverSocketChannel.accept();
		            		sc.configureBlocking(false);
		            		sc.register(selector, SelectionKey.OP_READ);
		            		sc.write(Charset.forName("UTF-8")
		                            .encode("你与聊天室里其他人都不是朋友关系，请注意隐私安全"));
		                }
                	}
                }
            }
            
        }
        
    }
    private void readHandler(SelectionKey key,Selector selector,Board board,int player) throws IOException{
        
        SocketChannel socketChannel=(SocketChannel) key.channel();
        ByteBuffer byteBuffer=ByteBuffer.allocate(1024);
        int x=0,y=0;
        while(socketChannel.read(byteBuffer)>0) {
            byteBuffer.flip();
            byte[] dstBytes=new byte[byteBuffer.remaining()];
            byteBuffer.get(dstBytes);
            String requestString=new String(dstBytes,"UTF-8");
            x=requestString.charAt(0)-'0';
            y=requestString.charAt(2)-'0';
            System.out.printf("%d %d\n",x,y);
            board.checkPutChess(player, x, y, true);
            String resultBoardString=board.getBoardinBuffer();
            Set<SelectionKey> allKey=selector.keys();
            Iterator<SelectionKey> iterator=allKey.iterator();
            while(iterator.hasNext()) {
                SelectionKey tmpKey=(SelectionKey) iterator.next();
                if(tmpKey.equals(key)) {
                    continue;
                }else {
                    SocketChannel targetChannel=(SocketChannel) tmpKey.channel();
                    try {
                        targetChannel.write(Charset.forName("UTF-8").encode(resultBoardString));
                    } catch (Exception e) {
                        
                    }
                }
            }
        }
    }

}
