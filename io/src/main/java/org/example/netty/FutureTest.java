package org.example.netty;

import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public class FutureTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        NioEventLoopGroup group = new NioEventLoopGroup(1);
        EventLoop eventLoop = group.next();

        //io.netty.util.concurrent.Future;
        Future<String> future = eventLoop.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(2000);
                return "1";
                //throw  new RuntimeException();
            }
        });

        //获取当前结果
        System.out.println(future.getNow());

        //同步阻塞获取
        System.out.println(future.get());

        //同步等待任务结束，有异常则抛出
        System.out.println(future.sync());

        //同步等待任务结束，有异常不会抛出
        System.out.println(future.await());
        //结果通过isSuccess判断
        System.out.println(future.isSuccess() ? future.getNow() : future.cause());

        //异步获取
        future.addListener(new GenericFutureListener<Future<? super String>>() {
            @Override
            public void operationComplete(Future<? super String> future) throws Exception {
                System.out.println(future.getNow());
            }
        });

        // promise可以绑定时间循环对象 并设置任务状态和结果，最终获取
        DefaultPromise<String> promise = new DefaultPromise<>(eventLoop);
        eventLoop.execute(new Runnable() {
            @Override
            public void run() {
                //设置成功的结果
                promise.setSuccess("成功");
                //设置失败的结果
                //promise.setSuccess("成功");
            }
        });
        promise.addListener(new GenericFutureListener<Future<String>>() {
            @Override
            public void operationComplete(Future<String> stringFuture) throws Exception {
                System.out.println(promise.getNow());
            }
        });

    }
}
