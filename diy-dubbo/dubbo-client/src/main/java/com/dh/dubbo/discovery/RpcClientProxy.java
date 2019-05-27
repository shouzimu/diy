package com.dh.dubbo.discovery;

import com.dh.dubbo.RpcRequest;
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

public class RpcClientProxy {

    private ServerDiscovery discovery;

    public RpcClientProxy(ServerDiscovery discovery) {
        this.discovery = discovery;
    }

    public <T> T create(final Class<T> interfaceClass) {
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[]{interfaceClass},
                (proxy, method, args) -> {
                    RpcRequest request = new RpcRequest();

                    request.setMethodName(method.getName());
                    request.setClassName(method.getDeclaringClass().getName());
                    request.setTypes(method.getParameterTypes());
                    request.setParams(args);

                    //接口地址
                    String serviceName = interfaceClass.getName();

                    //服务地址
                    String serviceAddress = discovery.discovery(serviceName);

                    String[] arrays = serviceAddress.split(":");
                    String url = arrays[0];
                    int port = Integer.parseInt(arrays[1]);

                    final RpcProxyHandler rpcProxyHandler = new RpcProxyHandler();
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
                                                .addLast(rpcProxyHandler);
                                    }
                                });
                        ChannelFuture future = bootstrap.connect(url, port).sync();
                        future.channel().writeAndFlush(request);
                        future.channel().closeFuture().sync();

                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        group.shutdownGracefully();
                    }
                    return rpcProxyHandler.getResponse();
                });

    }

}
