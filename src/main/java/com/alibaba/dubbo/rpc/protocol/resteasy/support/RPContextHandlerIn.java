package com.alibaba.dubbo.rpc.protocol.resteasy.support;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * @author cuikexiang
 *
 *         Create At 2016年4月25日 下午6:39:32
 */
public class RPContextHandlerIn extends SimpleChannelInboundHandler<FullHttpRequest> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
        // TODO Auto-generated method stub

    }

}
