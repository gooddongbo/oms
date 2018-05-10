package com.db.client.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.io.UnsupportedEncodingException;


public class TimeTwoHandler extends ChannelHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx){
        System.out.println("TimeTwoHandler的channelActive");
        ctx.fireChannelActive();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws UnsupportedEncodingException {
        System.out.println("TimeTwoHandler的channelRead");
        //将消息转发给下一个handler
        ctx.fireChannelRead(msg);
        System.out.println(ctx.channel().getClass().getName());

    }

    /**
     * 当发送异常时，打印异常日志，释放客户端资源
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        System.out.println (cause.getMessage());
        ctx.close();
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx){
        System.out.println("当channelHandler添加到channelPipeline中时被调用");
    }
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx){
        System.out.println("从channelHandle中移除到channelPipeline中时被调用");
    }

}
