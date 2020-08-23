package com.nio;
 
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;
 
/**
 * �ͻ����߳��࣬ר�Ž��շ���������Ӧ��Ϣ
 */
public class NioClientHandler implements Runnable {
    private Selector selector;
 
    public NioClientHandler(Selector selector) {
        this.selector = selector;
    }
 
    @Override
    public void run() {
 
        try {
            for (;;) {
                int readyChannels = selector.select();
 
                if (readyChannels == 0) continue;
 
                /**
                 * ��ȡ����channel�ļ���
                 */
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
 
                Iterator iterator = selectionKeys.iterator();
 
                while (iterator.hasNext()) {
                    /**
                     * selectionKeyʵ��
                     */
                    SelectionKey selectionKey = (SelectionKey) iterator.next();
 
                    /**
                     * **�Ƴ�Set�еĵ�ǰselectionKey**
                     */
                    iterator.remove();
 
                    /**
                     * 7. ���ݾ���״̬�����ö�Ӧ��������ҵ���߼�
                     */
 
                    /**
                     * ����� �ɶ��¼�
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
     * �ɶ��¼�������
     */
    private void readHandler(SelectionKey selectionKey, Selector selector)
            throws IOException {
        /**
         * Ҫ�� selectionKey �л�ȡ���Ѿ�������channel
         */
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
 
        /**
         * ���� buffer
         */
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
 
        /**
         * ѭ����ȡ����������Ӧ��Ϣ
         */
        String response = "";
        while (socketChannel.read(byteBuffer) > 0) {
            /**
             * �л�bufferΪ��ģʽ
             */
            byteBuffer.flip();
 
            /**
             * ��ȡbuffer�е�����
             */
            response += Charset.forName("UTF-8").decode(byteBuffer);
        }
 
        /**
         * ��channel�ٴ�ע�ᵽselector�ϣ��������Ŀɶ��¼�
         */
        socketChannel.register(selector, SelectionKey.OP_READ);
 
        /**
         * ������������Ӧ��Ϣ��ӡ������
         */
        if (response.length() > 0) {
            System.out.println(response);
        }
    }
}