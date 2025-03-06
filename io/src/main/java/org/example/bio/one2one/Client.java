package org.example.bio.one2one;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;


public class Client {
    public static void main(String[] args) {
        try ( Socket socket = new Socket("127.0.0.1",9999);){
            OutputStream os = socket.getOutputStream();

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            Scanner scanner = new Scanner(System.in);

            while (true){
                System.out.println("请输入:");
                String line = scanner.nextLine();
                bw.write(line);
                bw.newLine();
                bw.flush();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
