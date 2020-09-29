package chen.wind;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class ReversiClientHandler implements Runnable{
	private Selector selector;
	public ReversiClientHandler(Selector selector) {
        this.selector = selector;
    }
	
	@Override
    public void run() {
 
        try {
            for (;;) {
                int readyChannels = selector.select();
 
                if (readyChannels == 0) continue;
 
                /**
                 * 获取可用channel的集合
                 */
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
 
                Iterator iterator = selectionKeys.iterator();
 
                while (iterator.hasNext()) {
                    /**
                     * selectionKey实例
                     */
                    SelectionKey selectionKey = (SelectionKey) iterator.next();
 
                    /**
                     * **移除Set中的当前selectionKey**
                     */
                    iterator.remove();
 
                    /**
                     * 7. 根据就绪状态，调用对应方法处理业务逻辑
                     */
 
                    /**
                     * 如果是 可读事件
                     */
                    if (selectionKey.isReadable()) {
                        readHandler(selectionKey, selector);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    /**
     * 可读事件处理器
     */
    public void handleInputFromServer(Selector selector,SelectionKey key)
    		throws Exception{
        if(key.isValid()) {
            SocketChannel sc=(SocketChannel )key.channel();
            if(key.isConnectable()){
                if(sc.finishConnect()) {
                    sc.register(selector, SelectionKey.OP_READ);
                }
            }
            if(key.isReadable()) {
                readHandler(key, selector);
            }
        }
        
    }
    private void readHandler(SelectionKey selectionKey, Selector selector)
            throws IOException {
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        String response = "";
        while (socketChannel.read(byteBuffer) > 0) {
            byteBuffer.flip();
            response += Charset.forName("UTF-8").decode(byteBuffer);
        }
        socketChannel.register(selector, SelectionKey.OP_READ);
        if (response.length() > 0) {
            System.out.println(response);
        }
    }
}
