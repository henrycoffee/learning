package org.example.netty;

import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.NettyRuntime;

import java.util.concurrent.TimeUnit;

public class EventLoopTest {
    public static void main(String[] args) {

        // 系统CPU核心数
        System.out.println(NettyRuntime.availableProcessors());
        System.out.println(Runtime.getRuntime().availableProcessors());

        //指定事件循环对象数量：2
        NioEventLoopGroup group = new NioEventLoopGroup(2);

        //获取事件循环对象
        System.out.println(group.next()); //对象1
        System.out.println(group.next()); //对象2
        System.out.println(group.next()); //对象1
        //io.netty.channel.nio.NioEventLoop@de0a01f
        //io.netty.channel.nio.NioEventLoop@4c75cab9
        //io.netty.channel.nio.NioEventLoop@de0a01f

        //处理普通事件
        group.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("hello!");
            }
        });

        //周期性处理事件
        group.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("hello!");
            }
        },0,1, TimeUnit.SECONDS);



    }

}
