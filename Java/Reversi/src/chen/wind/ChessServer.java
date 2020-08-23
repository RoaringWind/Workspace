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
import java.util.Set;



public class ChessServer {

    public static void main(String[] args) throws Exception{
        // TODO Auto-generated method stub
        new ChessServer().start();
    }
    
    public void start() throws IOException{
        int serverPort=8001;
        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel=ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(serverPort));
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("服务器启动成功!");
        while(true) {
            int readyChannel=selector.select();
            if (readyChannel==0)continue;
            LinkedHashSet<SelectionKey> selectionKeysSet=(LinkedHashSet<SelectionKey>) selector.keys();//获取所有接入的channel,selectedkeys是部分
            HashMap<SelectionKey, Integer> playerMap=new HashMap<SelectionKey, Integer>();
            if(selectionKeysSet.size()==2) {
                ArrayList<SelectionKey> keyList=new ArrayList<SelectionKey>();
                Iterator iterator=selectionKeysSet.iterator();
                while(iterator.hasNext()) {
                    keyList.add((SelectionKey)iterator.next());
                }
                playerMap.put(keyList.get(0), 1);
                playerMap.put(keyList.get(1), -1);
                Board board=new Board();
                while(true) {
                    Set<SelectionKey> selectedKeys=selector.selectedKeys();
                    Iterator iterator2=selectedKeys.iterator();
                    while(iterator2.hasNext()) {
                        SelectionKey selectedKey=(SelectionKey) iterator2.next();
                        iterator2.remove();
                        if(selectedKey.isReadable()) {
                            readHandler(selectedKey, selector, board, playerMap.get(selectedKey));
                        }
                    }
                }
            }
            
        }
        
    }
    private void readHandler(SelectionKey key,Selector selector,Board board,int player) throws IOException{
        
        SocketChannel socketChannel=(SocketChannel) key.channel();
        ByteBuffer byteBuffer=ByteBuffer.allocate(1024);
        int readBytes=socketChannel.read(byteBuffer);
        int x=0,y=0;
        if(readBytes>0) {
            byteBuffer.flip();
            byte[] dstBytes=new byte[byteBuffer.remaining()];
            byteBuffer.get(dstBytes);
            String requestString=new String(dstBytes,"UTF-8");
            x=requestString.charAt(0)-'0';
            y=requestString.charAt(2)-'0';
            board.checkPutChess(player, x, y, true);
            String resultBoardString=board.getBoardinBuffer();
            Set<SelectionKey> allKey=selector.keys();
            Iterator iterator=allKey.iterator();
            while(iterator.hasNext()) {
                SelectionKey tmpKey=(SelectionKey) iterator.next();
                if(tmpKey.equals(key)) {
                    continue;
                }else {
                    SocketChannel targetChannel=(SocketChannel) tmpKey.channel();
                    try {
                        targetChannel.write(Charset.forName("UTF-8").encode(resultBoardString));
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                }
            }
        }else if (readBytes<0) {
            key.cancel();
            socketChannel.close();
        }else{;}
        
    }

}
