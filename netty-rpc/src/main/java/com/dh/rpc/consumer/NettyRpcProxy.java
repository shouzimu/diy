package com.dh.rpc.consumer;

import com.dh.rpc.protocol.NettyRpcProtocol;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import java.lang.reflect.Proxy;

public class NettyRpcProxy {

    public <T> T create(final Class<T> interfaceClass) {
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[]{interfaceClass},
                (proxy, method, args) -> {
                    NettyRpcProtocol request = new NettyRpcProtocol();

                    request.setMethodName(method.getName());
                    request.setClassName(method.getDeclaringClass().getName());
                    request.setTypes(method.getParameterTypes());
                    request.setParams(args);

                    //接口地址
                    String serviceName = interfaceClass.getName();

                    //服务地址

                    ConsumerHandler handler = new ConsumerHandler();
                    EventLoopGroup group = new NioEventLoopGroup();
                    try {
                        Bootstrap bootstrap = new Bootstrap();
                        bootstrap.group(group).channel(NioSocketChannel.class)
                                .option(ChannelOption.TCP_NODELAY, Boolean.TRUE)
                                .handler(new ChannelInitializer<SocketChannel>() {
                                    @Override
                                    protected void initChannel(SocketChannel ch) throws Exception {
                                        ch.pipeline()
                                                .addLast("decoder",
                                                        new ObjectDecoder(ClassResolvers.cacheDisabled(null)))
                                                .addLast("encoder", new ObjectEncoder())
                                                .addLast(handler);
                                    }
                                });
                        ChannelFuture future = bootstrap.connect("127.0.0.1", 8080).sync();
                        future.channel().writeAndFlush(request);
                        future.channel().closeFuture().sync();

                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        group.shutdownGracefully();
                    }
                    return handler.getResponse();
                });

    }

}
