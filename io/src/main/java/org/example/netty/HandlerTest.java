package org.example.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class HandlerTest {
    public static void main(String[] args) {

        ServerBootstrap bootstrap = new ServerBootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioServerSocketChannel.class);

        bootstrap.childHandler(new ChannelInitializer<NioSocketChannel>() {

            // handler的addLast()排序 : head - h1 - h2 - h3 - h4 - tail
            @Override
            protected void initChannel(NioSocketChannel channel) throws Exception {
                //进站1
                channel.pipeline().addLast("h1", new ChannelInboundHandlerAdapter() {
                    @Override
                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                        System.out.println(1);
                        //为了将上下文和消息传递给下个handler
                        super.channelRead(ctx, msg);
                    }
                });
                //进站2
                channel.pipeline().addLast("h2",new ChannelInboundHandlerAdapter() {
                    @Override
                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                        System.out.println(2);
                        //为了将上下文和消息传递给下个handler
                        //同 super.channelRead(ctx, msg);
                        //ctx.fireChannelRead(msg);

                        //或者写出数据,如果调用此方法输出 则不再往下个入站handler传递消息，而是去从tail往前找出站handler
                        channel.writeAndFlush("输出消息!");

                        //或者写出数据,如果调用此方法输出 则不再往下个入站handler传递消息，而是去从当前handler的位置向head方向找出站handler
                        //当然writeAndFlush()只能写一次
                        //ctx.writeAndFlush("输出消息!");
                    }
                });

                //出站是addLast倒序执行
                //出站1
                channel.pipeline().addLast("h3",new ChannelOutboundHandlerAdapter() {
                    @Override
                    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                        System.out.println(3);
                        super.write(ctx, msg, promise);
                    }
                });
                //出站2
                channel.pipeline().addLast("h4",new ChannelOutboundHandlerAdapter() {
                    @Override
                    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                        System.out.println(4);
                        super.write(ctx, msg, promise);
                    }
                });
            }
        });

        bootstrap.bind(9999);
    }
}
