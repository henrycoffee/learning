package org.example.bio.one2one;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class Sever {

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(9999);

            //阻塞等待连接
            Socket socket = serverSocket.accept();

            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String msg =null;
            // 同步阻塞按行收取消息
            while ((msg = br.readLine()) != null) {
                System.out.println("服务端收到消息:" + msg);
            }
        } catch (Exception e) {
            // 未收取到消息，但是客户端G了(客户端没有 socket.close())，
            // 就会报java.net.SocketException: Connection reset
           e.printStackTrace();
        }
    }
}
