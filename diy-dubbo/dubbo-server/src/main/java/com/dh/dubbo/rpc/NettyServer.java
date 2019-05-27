package com.dh.dubbo.rpc;

import com.dh.dubbo.registry.RegistryService;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.util.concurrent.DefaultThreadFactory;
import java.lang.reflect.AnnotatedType;
import java.util.HashMap;
import java.util.Map;
import lombok.Data;

/**
 * 使用netty作为网络传输工具
 *
 * @date 2019-05-20 20:48
 */
@Data
public class NettyServer {


    private ServerBootstrap bootstrap;

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    private RegistryService registryService;

    private String serviceAddress;

    private Map<String, Object> serviceMap;


    public void publisher() {
        serviceMap.forEach((k, v) -> {
            registryService.register(k, serviceAddress);
        });

        bootstrap = new ServerBootstrap();

        //主线程池，接收客户端连接
        bossGroup = new NioEventLoopGroup(1, new DefaultThreadFactory("NettyServerBoss", true));
        //工作线程池，处理客户端连接
        workerGroup = new NioEventLoopGroup(2,
                new DefaultThreadFactory("NettyServerWorker", true));

        final ServerHandler serverHandler = new ServerHandler(serviceMap);

        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childOption(ChannelOption.TCP_NODELAY, Boolean.TRUE)
                .childOption(ChannelOption.SO_REUSEADDR, Boolean.TRUE)
                .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                //定义字处理器，用于处理接收到的消息
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline()
                                .addLast("decoder", new ObjectDecoder(ClassResolvers.cacheDisabled(null)))
                                .addLast("encoder", new ObjectEncoder())
                                .addLast(serverHandler);
                    }
                });
        // bind
        try {
            ChannelFuture channelFuture = bootstrap.bind(9090).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void bindService(Object... service) {
        serviceMap = new HashMap<>(service.length);
        for (Object o : service) {
            Class<?> zlass = o.getClass();
            AnnotatedType[] types = zlass.getAnnotatedInterfaces();
            AnnotatedType Interface = types[0];
            String interfaceName = Interface.getType().getTypeName();
            serviceMap.put(interfaceName, o);
        }

    }


}
