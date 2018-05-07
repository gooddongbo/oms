package com.db.client.netty;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

public class TimeChannelFutureListener implements ChannelFutureListener {

    /**
     * 监听channelFuture,在channelFuture操作完成后获得通知
     * */
    @Override
    public void operationComplete(ChannelFuture channelFuture) throws Exception {
        if(channelFuture.isSuccess()){
            System.out.println("channelFuture 调用成功");
        }else{
            System.out.println("channelFuture 调用失败");
            Throwable throwable = channelFuture.cause();
            throwable.printStackTrace();
        }
    }
}
