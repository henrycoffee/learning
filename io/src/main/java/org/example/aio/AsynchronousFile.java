package org.example.aio;


import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;

public class AsynchronousFile {
    public static void main(String[] args) throws IOException {
        AsynchronousFileChannel afc = AsynchronousFileChannel.open(
                new File("io/src/main/java/org/example/aio/test.txt").toPath());

        System.out.println("线程:{" + Thread.currentThread().getName() + "}读开始。。。");
        ByteBuffer bf = ByteBuffer.allocate(1024);
        afc.read(bf, bf.position(), bf, new CompletionHandler<Integer, ByteBuffer>() {
            public void completed(Integer result, ByteBuffer attachment) {
                while (attachment.hasRemaining()) {
                    System.out.print((char) attachment.get());
                }
                System.out.println("");
                System.out.println("线程:{" + Thread.currentThread().getName() + "}读结束。。。");
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                System.out.println(exc.fillInStackTrace());
            }
        });

        System.out.println("线程:{" + Thread.currentThread().getName() + "}读完成。。。");

        //回调线程为守护线程，防止主线程退出，守护线程也推出
        System.in.read();

    }
}
