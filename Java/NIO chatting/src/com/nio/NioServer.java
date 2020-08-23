package com.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;
 
/**
 * NIO��������
 */
public class NioServer {
 
    /**
     * ������
     * @param args
     */
    public static void main(String[] args) throws IOException {
        new NioServer().start();
    }
 
    /**
     * ����
     */
    public void start() throws IOException {
        /**
         * 1. ����Selector
         */
        Selector selector = Selector.open();
 
        /**
         * 2. ͨ��ServerSocketChannel����channelͨ��
         */
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
 
        /**
         * 3. Ϊchannelͨ���󶨼����˿�
         */
        serverSocketChannel.bind(new InetSocketAddress(8000));
 
        /**
         * 4. **����channelΪ������ģʽ**
         */
        serverSocketChannel.configureBlocking(false);
 
        /**
         * 5. ��channelע�ᵽselector�ϣ����������¼�
         */
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("�����������ɹ���");
 
        /**
         * 6. ѭ���ȴ��½��������
         */
        for (;;) { // while(true) c for;;
            /**
             * TODO ��ȡ����channel����
             */
            int readyChannels = selector.select();
 
            /**
             * TODO ΪʲôҪ����������
             */
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
                 * ����� �����¼�
                 */
                if (selectionKey.isAcceptable()) {
                    acceptHandler(serverSocketChannel, selector);
                }
 
                /**
                 * ����� �ɶ��¼�
                 */
                if (selectionKey.isReadable()) {
                    readHandler(selectionKey, selector);
                }
            }
        }
    }
 
    /**
     * �����¼�������
     */
    private void acceptHandler(ServerSocketChannel serverSocketChannel,
                               Selector selector)
            throws IOException {
        /**
         * ���Ҫ�ǽ����¼�������socketChannel
         */
        SocketChannel socketChannel = serverSocketChannel.accept();
 
        /**
         * ��socketChannel����Ϊ����������ģʽ
         */
        socketChannel.configureBlocking(false);
 
        /**
         * ��channelע�ᵽselector�ϣ����� �ɶ��¼�
         */
        socketChannel.register(selector, SelectionKey.OP_READ);
 
        /**
         * �ظ��ͻ�����ʾ��Ϣ
         */
        socketChannel.write(Charset.forName("UTF-8")
                .encode("�����������������˶��������ѹ�ϵ����ע����˽��ȫ"));
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
         * ����buffer
         */
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
 
        /**
         * ѭ����ȡ�ͻ���������Ϣ
         */
        String request = "";
        while (socketChannel.read(byteBuffer) > 0) {
            /**
             * �л�bufferΪ��ģʽ
             */
            byteBuffer.flip();
 
            /**
             * ��ȡbuffer�е�����
             */
            request += Charset.forName("UTF-8").decode(byteBuffer);
        }
 
        /**
         * ��channel�ٴ�ע�ᵽselector�ϣ��������Ŀɶ��¼�
         */
        socketChannel.register(selector, SelectionKey.OP_READ);
 
        /**
         * ���ͻ��˷��͵�������Ϣ �㲥�������ͻ���
         */
        if (request.length() > 0) {
            // �㲥�������ͻ���
            broadCast(selector, socketChannel, request);
        }
    }
 
    /**
     * �㲥�������ͻ���
     */
    private void broadCast(Selector selector,
                           SocketChannel sourceChannel, String request) {
        /**
         * ��ȡ�������ѽ���Ŀͻ���channel
         */
        Set<SelectionKey> selectionKeySet = selector.keys();
 
        /**
         * ѭ��������channel�㲥��Ϣ
         */
        selectionKeySet.forEach(selectionKey -> {
            Channel targetChannel = selectionKey.channel();
 
            // �޳�����Ϣ�Ŀͻ���
            if (targetChannel instanceof SocketChannel
                    && targetChannel != sourceChannel) {
                try {
                    // ����Ϣ���͵�targetChannel�ͻ���
                    ((SocketChannel) targetChannel).write(
                            Charset.forName("UTF-8").encode(request));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
 
 
 
}