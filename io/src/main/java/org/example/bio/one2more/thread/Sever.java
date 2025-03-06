package org.example.bio.one2more.thread;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Sever {

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(9999);

            while (true) {
                Socket socket = serverSocket.accept();
                /**
                 *  同步阻塞模型面对多个socket时，只能每个socket都分配一个线程去处理消息，否则没法同时接收socket多个消息
                 *  但是面对大量客户端socket，创建大量线程导致资源耗尽 且线程上下文资源切换频繁 性能差
                 */
                new Thread() {
                    public void run() {
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
                    }
                }.start();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


}
