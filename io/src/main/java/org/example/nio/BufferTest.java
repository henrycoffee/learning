package org.example.nio;


import java.nio.ByteBuffer;

public class BufferTest {
    public static void main(String[] args) {

        // Invariants: mark <= position <= limit <= capacity
        // 从JVM内存中分配 字节缓冲区
        ByteBuffer bf = ByteBuffer.allocate(10);
        // 从直接内存中分配 字节缓冲区
        ByteBuffer directBf = ByteBuffer.allocateDirect(10);

        //容量 10
        System.out.println(bf.capacity());
        //当前位置 0
        System.out.println(bf.position());
        //限制位置 10
        System.out.println(bf.limit());
        //标记 java.nio.HeapByteBuffer[pos=0 lim=10 cap=10]
        System.out.println(bf.mark());

        //放数据
        String s = "hello!";
        bf.put(s.getBytes());

        //bf.remaining() 此缓冲区中剩余的元素数
        System.out.println(bf.remaining());

        //限制设置为当前位置，然后将该位置设置为零。 丢弃标记。
        bf.flip();

        //取数据
        byte[] bytes = new byte[bf.remaining()];
        bf.get(bytes, bf.position(), bf.remaining());
        System.out.println(new String(bytes)); //hello!
        System.out.println(bf.mark());

        //倒带 位置设置为零，标记被丢弃。 用于重复的再次读取
        bf.rewind();
        byte[] bytes2 = new byte[bf.remaining()];
        bf.get(bytes2, bf.position(), bf.remaining());
        System.out.println(new String(bytes));//hello!

        //清除数据  位置设置为零，限制设置为容量，标记被丢弃。
        // 实际内存中的数据并没有被清除，但是所有的位置被恢复到初始状态
        bf.clear();
    }
}
