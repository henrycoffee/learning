package org.example.nio;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ChannelTest {

    public static void main(String[] args) {

        try (FileOutputStream os = new FileOutputStream("./test.txt")) {
            //FileOutputStream 可以获取到通道 ，但是直接的OutputStream获取不到
            FileChannel channel = os.getChannel();

            ByteBuffer bf = ByteBuffer.allocate(1024);
            bf.put("hello".getBytes());
            bf.flip();
            System.out.println(bf.remaining());

            //文件写入
            int write = channel.write(bf);
            System.out.println("以写入:{" + write + "}字节");

            //文件读取
            FileInputStream os2 = new FileInputStream("./test.txt");
            FileChannel channel2 = os2.getChannel();
            ByteBuffer bf2 = ByteBuffer.allocate(1024);
            while (channel2.read(bf2) != -1) {
                bf2.flip();
                System.out.println(new String(bf2.array(), 0, bf2.remaining()));
            }

            //文件拷贝
            bf.rewind();
            FileOutputStream os3 = new FileOutputStream("./test2.txt");
            FileChannel channel3 = os3.getChannel();
            channel3.write(bf2);

        } catch (Exception e) {
            throw new RuntimeException(e);

        }

    }

}
