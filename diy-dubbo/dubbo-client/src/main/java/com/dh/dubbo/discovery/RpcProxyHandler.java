package com.dh.dubbo.discovery;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.Data;

@Data
public class RpcProxyHandler extends ChannelInboundHandlerAdapter {

    private Object response;


    public static ChannelHandlerContext channelCtx;


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        channelCtx = ctx;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        response = msg;
    }
}
