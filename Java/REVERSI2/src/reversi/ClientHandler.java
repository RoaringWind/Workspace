package reversi;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

public class ClientHandler implements Runnable {
	private Selector selector=null;
	public ClientHandler(Selector selector) {
		this.selector=selector;
	}
	@Override
	public void run() {
		try {
			for(;;) {
				int readyChannels=selector.select();
				if(readyChannels==0) continue;
				Set<SelectionKey> selectionKeys=selector.selectedKeys();
				Iterator<SelectionKey> iterator=selectionKeys.iterator();
				while(iterator.hasNext()){
					SelectionKey selectionKey=iterator.next();
					iterator.remove();
					if(selectionKey.isReadable()) {
						clientReadHandler(selector,selectionKey);
					}
				}
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	private void clientReadHandler(Selector selector, SelectionKey selectionKey) 
			throws Exception{
		SocketChannel socketChannel=(SocketChannel) selectionKey.channel();
		ByteBuffer byteBuffer=ByteBuffer.allocate(1024);
		String boardInfoFromServerString="";
		while(socketChannel.read(byteBuffer)>0) {
			byteBuffer.flip();
			boardInfoFromServerString+=Charset.forName("UTF-8").decode(byteBuffer);
		}
		socketChannel.register(selector, SelectionKey.OP_READ);
		if(boardInfoFromServerString.length()>0) {
			System.out.println(boardInfoFromServerString);
		}
	}
}
