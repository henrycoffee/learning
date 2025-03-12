package org.example.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;


import static io.netty.buffer.ByteBufUtil.appendPrettyHexDump;
import static io.netty.util.internal.StringUtil.NEWLINE;

public class packetTest {
    public static void main(String[] args) {
        //通用handler 只打印报文
        ChannelInboundHandlerAdapter handlerAdapter = new ChannelInboundHandlerAdapter() {
            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                log((ByteBuf) msg);
                super.channelRead(ctx, msg);
            }
        };

        //粘包处理
        //1.固定长度切割
//        FixedLengthFrameDecoder fixedLengthFrameDecoder = new FixedLengthFrameDecoder(4);
//        EmbeddedChannel channel = new EmbeddedChannel(fixedLengthFrameDecoder, handlerAdapter);
//        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer(12);
//        buf.writeBytes("1234abcd".getBytes());
//        channel.writeInbound(buf);
//        buf.release();
//        channel.close();

        //2.长度属性切割
//        LengthFieldBasedFrameDecoder lengthFieldBasedFrameDecoder = new LengthFieldBasedFrameDecoder(1024, 0, 4,0,4);
//        EmbeddedChannel channel = new EmbeddedChannel(lengthFieldBasedFrameDecoder, handlerAdapter);
//        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer(12);
//        buf.writeInt(8);
//        buf.writeBytes("12345678".getBytes());
//        channel.writeInbound(buf);

        //2.行切割
//        LineBasedFrameDecoder lineBasedFrameDecoder = new LineBasedFrameDecoder(1024);
//        EmbeddedChannel channel = new EmbeddedChannel(lineBasedFrameDecoder, handlerAdapter);
//        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer(1024);
//        buf.writeBytes("1234\n".getBytes());
//        buf.writeBytes("abcd\n".getBytes());
//        channel.writeInbound(buf);

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
