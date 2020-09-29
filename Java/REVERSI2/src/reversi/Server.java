package reversi;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class Server {

	public static void main(String[] args) throws Exception{
		new Server().start();
	}
	public void start() throws Exception{
		int port=8005;
		Selector selector=Selector.open();
		ServerSocketChannel serverSocketChannel=ServerSocketChannel.open();
		serverSocketChannel.bind(new InetSocketAddress(port));
		serverSocketChannel.configureBlocking(false);
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		System.out.println("服务器启动成功！");
		HashMap<SelectionKey, Integer> playerMap=new HashMap<SelectionKey, Integer>();
		int playerNo=1;
		Board boardInServer=new Board();
		for(;;) {
			int readyChannels=selector.select();
			if(readyChannels==0)continue;
			Set<SelectionKey> selectionKeys=selector.selectedKeys();
			Iterator<SelectionKey> iterator0=selectionKeys.iterator();
			while (iterator0.hasNext()) {
				SelectionKey selectedKey=(SelectionKey)iterator0.next();
				iterator0.remove();
				if(playerMap.getOrDefault(selectedKey, 0)==0){
					playerMap.put(selectedKey, playerNo);
					playerNo*=-1;
					//System.out.println(playerNo);
				}
				if(selectedKey.isAcceptable()) {
					accpetHandler(serverSocketChannel, selector,playerNo);				
				}
				if(selectedKey.isReadable()) {
					int player=playerMap.get(selectedKey);
					readHandler(selectedKey, selector, boardInServer, player);
//					SocketChannel TTT=(SocketChannel) selectedKey.channel();
//					System.out.println(TTT.getRemoteAddress());
				}
				
			}
		}
	}
	private void readHandler(SelectionKey selectionKey,Selector selector,
			Board board,int player) 
			throws Exception {
		SocketChannel socketChannel=(SocketChannel) selectionKey.channel();
		ByteBuffer byteBuffer=ByteBuffer.allocate(1024);
		String request="";
		
		while(socketChannel.read(byteBuffer)>0) {
			byteBuffer.flip();
			request += Charset.forName("UTF-8").decode(byteBuffer);
		}
		//System.out.printf("received: %s",request);
		int x=request.charAt(0)-'0';
		int y=request.charAt(2)-'0';
		//System.out.printf("x: %d y: %d player %d\n",x,y,player);
		boolean SuccessfulPut=board.checkPutChess(player, x, y, true);
		socketChannel.register(selector, SelectionKey.OP_READ);
		if(SuccessfulPut) {
			String boardInfoString=board.getBoardinBuffer();
			Set<SelectionKey> allKeys=selector.keys();
			allKeys.forEach(eachKey -> {
	            Channel targetChannel = eachKey.channel();
	            // 剔除发消息的客户端
	            if (targetChannel instanceof SocketChannel
	                    && targetChannel != socketChannel) {
	                try {
	                    // 将信息发送到targetChannel客户端
	                    ((SocketChannel) targetChannel).write(
	                            Charset.forName("UTF-8").encode(boardInfoString));
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        });
		}
	}
	private void accpetHandler(ServerSocketChannel serverSocketChannel,
			Selector selector,int player) 
			throws Exception {
		SocketChannel socketChannel=serverSocketChannel.accept();
		socketChannel.configureBlocking(false);
		socketChannel.register(selector, SelectionKey.OP_READ);
		System.out.println(socketChannel.getRemoteAddress());
		socketChannel.write(Charset.forName("UTF-8").encode(String.format("欢迎开始下棋 player:%d", player*-1)));
	}

}
