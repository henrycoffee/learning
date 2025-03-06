package org.example.nio.selector;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try( Socket socket = new Socket("127.0.0.1", 9999);) {
            OutputStream os = socket.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

            Scanner scanner = new Scanner(System.in);
            while (true){
                System.out.println("请输入:");
                String s = scanner.nextLine();
                writer.write(s);
                writer.newLine();
                writer.flush();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}

