package com.dh.dubbo.rpc;

import com.dh.dubbo.RpcRequest;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.lang.reflect.Method;
import java.util.Map;


@Sharable
public class ServerHandler extends ChannelInboundHandlerAdapter {

    private Map<String, Object> handlerMap;

    public ServerHandler(Map<String, Object> handlerMap) {
        this.handlerMap = handlerMap;
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("服务端接收到消息：" + msg);
        RpcRequest rpcRequest = (RpcRequest) msg;
        Object result = new Object();
        if (handlerMap.containsKey(rpcRequest.getClassName())) {
            Object clazz = handlerMap.get(rpcRequest.getClassName());
            Method method = clazz.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getTypes());
            result = method.invoke(clazz, rpcRequest.getParams());
        }
        ctx.write(result);
        ctx.flush();
        ctx.close();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("hello world channelReadComplete");

        super.channelReadComplete(ctx);
    }
}
