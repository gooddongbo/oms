package com.db.client.NIO;

import com.db.client.NIO.util.CharsetHelper;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class TimeNioClient implements Runnable {

    private BlockingQueue<String> words;
    private Random random;

    public static void main(String[] args) {
        //种多个线程发起Socket客户端连接请求
            TimeNioClient c = new TimeNioClient();
            c.init();
            new Thread(c).start();
    }

    @Override
    public void run() {
        SocketChannel channel = null;
        Selector selector = null;
        try {
            channel = SocketChannel.open();
            channel.configureBlocking(false);
            //请求连接
            channel.connect(new InetSocketAddress("localhost", 9999));
            selector = Selector.open();
            channel.register(selector, SelectionKey.OP_CONNECT);

            while (true) {
                selector.select();
                Iterator ite = selector.selectedKeys().iterator();
                while (ite.hasNext()) {
                    SelectionKey key = (SelectionKey) ite.next();
                    ite.remove();
                    //此键的通道是否已完成其套接字连接操作
                    if (key.isConnectable()) {
                        SocketChannel test = (SocketChannel) key.channel();
                        //SocketChannel正在尝试连接
                        if (test.isConnectionPending()) {
                            if (test.finishConnect()) {
                                test.configureBlocking(false);
                                test.write(CharsetHelper.encode(CharBuffer.wrap("hello nio server")));
                                test.register(selector,SelectionKey.OP_READ);
                                sleep();
                            } else {
                                key.cancel();
                            }
                        }
                    } else if (key.isReadable()) {
                        ByteBuffer byteBuffer = ByteBuffer.allocate(128);
                        channel.read(byteBuffer);
                        byteBuffer.flip();
                        CharBuffer charBuffer = CharsetHelper.decode(byteBuffer);
                        String answer = charBuffer.toString();
                        System.out.println(Thread.currentThread().getId() + "---" + answer);

                        String word = getWord();
                        if (word != null) {
                            channel.write(CharsetHelper.encode(CharBuffer.wrap(word)));
                        }
                        sleep();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (channel != null) {
                try {
                    channel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (selector != null) {
                try {
                    selector.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void init() {
        words = new ArrayBlockingQueue<String>(5);
        try {
            words.put("hi");
            words.put("who");
            words.put("what");
            words.put("where");
            words.put("bye");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        random = new Random();
    }

    private String getWord(){
        return words.poll();
    }

    private void sleep() {
        try {
            TimeUnit.SECONDS.sleep(random.nextInt(3));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void sleep(long l) {
        try {
            TimeUnit.SECONDS.sleep(l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
