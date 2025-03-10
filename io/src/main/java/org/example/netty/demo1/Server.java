package org.example.netty.demo1;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

public class Server {
    public static void main(String[] args) {
        ServerBootstrap bootstrap = new ServerBootstrap();

        //处理消息的普通事件循环组
        DefaultEventLoopGroup defaultEventLoopGroup = new DefaultEventLoopGroup();

        // 第一个事件循环组负责接收事件 只需要一个线程即可， 第二个事件循环租负责其他类型的事件
        bootstrap.group(new NioEventLoopGroup(), new NioEventLoopGroup(2))
                .channel(NioServerSocketChannel.class)
                .childHandler(
                        new ChannelInitializer<NioSocketChannel>() {
                            @Override
                            protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                                nioSocketChannel.pipeline().addLast(new StringDecoder());

                                //还是使用NioEventLoopGroup中的线程取处理消息
                                nioSocketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                        System.out.println(msg);
                                        //传递给管道中的下一个
                                        ctx.fireChannelRead(msg);
                                    }
                                });

                                // 使用处理消息的普通事件循环组的线程组，不占用NIO事件循环组的线程，提高并发能力
                                nioSocketChannel.pipeline().addLast(defaultEventLoopGroup, "default", new ChannelInboundHandlerAdapter() {
                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                        System.out.println("使用处理消息的普通事件循环组线程处理消息,接收到:+ msg");
                                    }
                                });
                            }
                        }
                )
                .bind(9999);


    }
}
