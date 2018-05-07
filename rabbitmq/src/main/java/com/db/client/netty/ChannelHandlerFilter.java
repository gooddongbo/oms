package com.db.client.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
/**
 * 出站: 客户端-> 服务器端
 * 入站:服务器端-> 客户端
 * */
public class ChannelHandlerFilter extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        //ChannelPipeline提供了ChannelHandler链的容器，并定义了用于在该链上传播入站和出战事件流得API
        channel.pipeline().addLast(new TimeTwoHandler()).addLast(new TimeClientHandler());
    }
}
