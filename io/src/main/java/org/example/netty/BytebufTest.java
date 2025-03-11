package org.example.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;

import static io.netty.buffer.ByteBufUtil.appendPrettyHexDump;
import static io.netty.util.internal.StringUtil.NEWLINE;

public class BytebufTest {
    public static void main(String[] args) {
        //默认为 分配直接内存  初始化容量  和 最大容量
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer(5, 1024);
        //创建 JVM内存
        ByteBufAllocator.DEFAULT.heapBuffer(5);
        //创建 直接内存
        ByteBufAllocator.DEFAULT.directBuffer(5);

        //写 最大的优点可以动态扩容
        log(buf); //PooledUnsafeDirectByteBuf(ridx: 0, widx: 0, cap: 5)
        for (int i = 0; i < 10; i++) {
            buf.writeByte(i);
        }
        log(buf);//PooledUnsafeDirectByteBuf(ridx: 0, widx: 10, cap: 16)


        //由于有读写两个位置指针，所以并不需要 flip方法
        //读 读指针会移动
        System.out.println(buf.readByte());
        log(buf);

        //都 读指针不会移动
        System.out.println(buf.getByte(2));

        //池化
        //池化功能是否开启，可以通过下面的系统环境变量来设置或者JVM参数
        //-Dio.netty.allocator.type={unpooled|pooled}

        //切片 可以实现逻辑层面零拷贝
        ByteBuf slice1 = buf.slice(0, 5);
        ByteBuf slice2 = buf.slice(5, 5);
        log(slice1);
        log(slice2);

        //最终切片和原始的buf都是同一块内存，如果写入值，也都会发生变化
        // 1.但是切片后的得到的buf，容量会限制 不支持再扩容
        // 2.原有的buf release后，切边将不能再使用
        System.out.println();
        System.out.println();
        slice1.setByte(0, 9);
        log(slice1);
        log(buf);

        //释放 内存回收 内部实现是指针计数法，池和非池 JVM和直接内存略有不同
        buf.release();

        //引用计数加1
        // 切片这样操作，可以防止原始buf直接释放影响切片，切片retain使用完后自己再release
        slice1.retain();

        //完整切片 内容完全和原始buf一样，同一块内存
        buf.duplicate();

        //拷贝 复制到新的buf和原始buf没有关联
        buf.copy();

        //合并多个buf到一个buf  配合retain保留引用计数 使用完后自己再release
        CompositeByteBuf compositeBuf = ByteBufAllocator.DEFAULT.compositeBuffer();
        compositeBuf.addComponents(true, slice1, slice2);

    }

    private static void log(ByteBuf buffer) {
        int length = buffer.readableBytes();
        int rows = length / 16 + (length % 15 == 0 ? 0 : 1) + 4;
        StringBuilder buf = new StringBuilder(rows * 80 * 2)
                .append("read index:").append(buffer.readerIndex())
                .append(" write index:").append(buffer.writerIndex())
                .append(" capacity:").append(buffer.capacity())
                .append(NEWLINE);
        appendPrettyHexDump(buf, buffer);
        System.out.println(buf.toString());
    }


}
