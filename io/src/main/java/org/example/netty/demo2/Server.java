package org.example.netty.demo2;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Server {

    public static void main(String[] args) {

        DefaultEventLoopGroup group = new DefaultEventLoopGroup(2);

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(new NioEventLoopGroup(), new NioEventLoopGroup(2))
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel channel) throws Exception {
                        channel.pipeline().addLast(new StringDecoder(StandardCharsets.UTF_8));

                        channel.pipeline().addLast(group, new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                System.out.println(ctx.channel().remoteAddress() + " : " + msg);
                                super.channelRead(ctx, msg);
                            }
                        });

                        channel.pipeline().addLast(group, new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                String msgString = msg.toString();
                                if ("chat".equals(msgString)) {
                                    System.out.println(channel.remoteAddress() + "请求开始聊天");
                                    group.execute(new Runnable() {
                                        @Override
                                        public void run() {
                                            synchronized (channel) {
                                                Scanner scanner = new Scanner(System.in);
                                                System.out.println("回复" + channel.remoteAddress() + ":");
                                                String s = scanner.nextLine();
                                                if ("exit".equals(s)) {
                                                    ctx.channel().writeAndFlush("exit");
                                                } else {
                                                    ctx.channel().writeAndFlush(s);
                                                }
                                            }
                                        }
                                    });

                                } else if ("exit".equals(msg)) {
                                    System.out.println("客户端请求退出聊天");
                                    ctx.fireChannelRead("exit");
                                } else {
                                    ctx.fireChannelRead(msg);
                                }
                            }
                        });
                        channel.pipeline().

                                addLast(new StringEncoder(StandardCharsets.UTF_8));
                    }
                });

        bootstrap.bind(9999);
    }
}
