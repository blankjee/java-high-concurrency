package com.blankjee.code.niodemo;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author blankjee
 * @Date 2020/8/24 16:50
 */
public class NioDemo {
    private Selector selector;
    private ExecutorService tp = Executors.newCachedThreadPool();

    // 统计服务器线程在一个客户端上花费的时间
    public static Map<Socket, Long> time_stat = new HashMap<>(10240);

    private void startServer() throws IOException {
        // 通过工厂方法获得一个Selector对象的实例
        selector = SelectorProvider.provider().openSelector();
        ServerSocketChannel ssc = ServerSocketChannel.open();
        // 设置为非阻塞式
        ssc.configureBlocking(false);

        InetSocketAddress isa = new InetSocketAddress(InetAddress.getLocalHost(), 8000);
        // InetSocketAddress isa = new InetSocketAddress(8000);
        ssc.socket().bind(isa);

        // 将ServerSocketChannel绑定到Selector，并指定感兴趣的时间点为ACCEPT
        SelectionKey acceptKey = ssc.register(selector, SelectionKey.OP_ACCEPT);

        for (;;) {
            // 这是一个阻塞方法，如果当前没有任何数据准备好，他就会等待
            selector.select();
            Set readyKey = selector.selectedKeys();
            Iterator i = readyKey.iterator();
            long e = 0;
            while (i.hasNext()) {
                SelectionKey sk = (SelectionKey) i.next();
                i.remove();

                if (sk.isAcceptable()) {
                    doAccept(sk);
                } else if (sk.isValid() && sk.isReadable()) {
                    if (!time_stat.containsKey(((SocketChannel) sk.channel()).socket())) {
                        time_stat.put(((SocketChannel) sk.channel()).socket(), System.currentTimeMillis());
                    }
                    doRead(sk);
                } else if (sk.isValid() && sk.isWritable()) {
                    doWrite(sk);
                    e = System.currentTimeMillis();
                    long b = time_stat.remove(((SocketChannel) sk.channel()).socket());
                    System.out.println("spend:" + (e - b) + "ms");
                }
            }
        }
    }

    private void doAccept(SelectionKey sk) {
        ServerSocketChannel server = (ServerSocketChannel) sk.channel();
        SocketChannel clientChannel;
        try {
            clientChannel = server.accept();
            clientChannel.configureBlocking(false);

            // Register this channel for reading.
            SelectionKey clientKey = clientChannel.register(selector, SelectionKey.OP_READ);
            // Allocate an EchoClient instance and attach it to this selection key.
            EchoClient echoClient = new EchoClient();
            clientKey.attach(echoClient);

            InetAddress clientAddress = clientChannel.socket().getInetAddress();
            System.out.println("Accepted connection from " + clientAddress.getHostAddress() + ".");
        } catch (Exception e) {
            System.err.println("Fail to accept new client.");
            e.printStackTrace();
        }
    }

    private void doRead(SelectionKey sk) {
        SocketChannel channel = (SocketChannel) sk.channel();
        ByteBuffer bb = ByteBuffer.allocate(8192);
        int len;

        try {
            len = channel.read(bb);
            if (len < 0) {
                disconnect(sk);
                return;
            }
        } catch (Exception e) {
            System.out.println("Failed to read from client.");
            e.printStackTrace();
            disconnect(sk);
            return;
        }
        bb.flip();
        tp.execute(new HandleMsg(sk, bb));
    }

    private void doWrite(SelectionKey sk) {
        SocketChannel channel = (SocketChannel) sk.channel();
        EchoClient echoClient = (EchoClient) sk.attachment();
        LinkedList<ByteBuffer> outq = echoClient.getOutputQueue();

        ByteBuffer bb = outq.getLast();
        try {
            int len = channel.write(bb);
            if (len == -1) {
                disconnect(sk);
                return;
            }
            if (bb.remaining() == 0) {
                // The buffer was completely written, remove it.
                outq.removeLast();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (outq.size() == 0) {
            sk.interestOps(SelectionKey.OP_READ);
        }
    }

    private void disconnect(SelectionKey sk) {
        sk.cancel();
    }


    class EchoClient {
        private LinkedList<ByteBuffer> outq;
        EchoClient() {
            outq = new LinkedList<>();
        }
        public LinkedList<ByteBuffer> getOutputQueue() {
            return outq;
        }
        public void enqueue(ByteBuffer bb) {
            outq.addFirst(bb);
        }
    }

    class HandleMsg implements Runnable {
        SelectionKey sk;
        ByteBuffer bb;
        public HandleMsg(SelectionKey sk, ByteBuffer bb) {
            this.sk = sk;
            this.bb = bb;
        }

        @Override
        public void run() {
            EchoClient echoClient = (EchoClient) sk.attachment();
            echoClient.enqueue(bb);
            sk.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
            // 强迫selector立即返回
            selector.wakeup();
        }
    }
}
