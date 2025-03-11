package org.example.netty.demo2;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new StringDecoder(StandardCharsets.UTF_8));
                        socketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                System.out.println(ctx.channel().remoteAddress() + " : " + msg);
                                super.channelRead(ctx, msg);
                            }
                        });

                        socketChannel.pipeline().addLast(new StringEncoder(StandardCharsets.UTF_8));
                    }
                });
        ChannelFuture future = bootstrap.connect("127.0.0.1", 9999);
        future.sync();
        while (true) {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("请输入回复服务端的消息:");
                String s = scanner.nextLine();
                if ("exit".equals(s)) {
                    future.channel().writeAndFlush("exit");
                    break;
                }
                future.channel().writeAndFlush(s);
            }
        }

    }
}
