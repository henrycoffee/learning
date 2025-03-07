package org.example.nio.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Set;

public class Server {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel channel = ServerSocketChannel.open();
        channel.configureBlocking(false);
        channel.bind(new InetSocketAddress(9999));

        Selector selector = Selector.open();
        channel.register(selector, SelectionKey.OP_ACCEPT);

        while ( selector.select() >0) {
            Set<SelectionKey> keys = selector.selectedKeys();
            for (SelectionKey key : keys) {
                if (key.isAcceptable()){
                    SocketChannel socketChannel = channel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                }else if (key.isReadable()){
                    SelectableChannel selectableChannel = key.channel();
                    SocketChannel readChannel = (SocketChannel) selectableChannel;
                    ByteBuffer bf = ByteBuffer.allocate(1024);
                    while (readChannel.read(bf) >0 ){
                        bf.flip();
                        System.out.println(new String(bf.array(), 0, bf.limit()));
                        bf.clear();
                    }
                }
                keys.remove(key);
            }
        }









    }

}
