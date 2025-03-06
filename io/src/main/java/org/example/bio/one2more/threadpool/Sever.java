package org.example.bio.one2more.threadpool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Sever {


    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(9999);
            /**
             * 使用线程池处理多个socket的连接，只适合连接数较少的情况 或者连接较短的情况
             * 当socket连接大于线程池的最大连接数时，依旧会阻塞
             */
            ExecutorService executor = Executors.newFixedThreadPool(2);
            while (true) {
                Socket socket = serverSocket.accept();
                executor.execute(()->{
                    InputStream is = null;
                    try {
                        is = socket.getInputStream();
                        BufferedReader buffer = new BufferedReader(new InputStreamReader(is));
                        String s;
                        while ((s = buffer.readLine()) != null) {
                            System.out.println(Thread.currentThread().getName()
                                    + "收到Socket["+socket.getRemoteSocketAddress().toString()+"]消息:" + s);
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}
