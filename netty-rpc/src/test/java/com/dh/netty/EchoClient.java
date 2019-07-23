package com.dh.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class EchoClient {

    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();

        ClientChannelHandler handler = new ClientChannelHandler();
        bootstrap.group(new NioEventLoopGroup()).channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline()
                                .addLast("decoder", new ObjectDecoder(ClassResolvers.cacheDisabled(null)))
                                .addLast("encoder", new ObjectEncoder())
                                .addLast(new ObjectEncoder()).addLast(handler);

                    }
                });
        try {
            ChannelFuture future = bootstrap.connect("127.0.0.1", 8080).sync();
            future.channel().writeAndFlush("hello world");
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
