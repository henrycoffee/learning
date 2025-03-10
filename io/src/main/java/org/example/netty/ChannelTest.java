package org.example.netty;


import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

public class ChannelTest {

    public static void main(String[] args) throws InterruptedException {
        //创建
        NioSocketChannel channel = new NioSocketChannel();

        //读取和其他操作都用BootStrap添加handler完成

        //写数据
        channel.write("hello");
        channel.writeAndFlush("hello");

        //建立连接
        ChannelFuture channelFuture = channel.connect(new InetSocketAddress(9999));
        //1.可选择同步等待连接建立完成
        channelFuture.sync();
        //2.或者可以选择异步连接后完成回调
        channelFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                //todo
                System.out.println("异步连接完成");
            }
        });

        //关闭
        channel.close();
        //异步关闭
        ChannelFuture closeFuture = channel.closeFuture();
        //1.可选择同步等待关闭操作完成
        closeFuture.sync();
        //2.或者可以选择异步关闭后完成回调
        closeFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                //todo
                System.out.println("异步关闭完成");
            }
        });


    }
}
