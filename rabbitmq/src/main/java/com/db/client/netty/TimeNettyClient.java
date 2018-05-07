package com.db.client.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class TimeNettyClient {
    public void connect(int port, String host){
        EventLoopGroup group = new NioEventLoopGroup();
        //创建客户端辅助启动类
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group).channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY,true)
                .handler(new ChannelHandlerFilter());
        try {
            //发起异步连接操作
            ChannelFuture future = bootstrap.connect(host,port).sync();
            future.addListener(new TimeChannelFutureListener());
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            System.out.println(TimeNettyClient.class.getName()+" close");
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        int port = 9999;
        new TimeNettyClient().connect(port,"127.0.0.1");
    }
}
