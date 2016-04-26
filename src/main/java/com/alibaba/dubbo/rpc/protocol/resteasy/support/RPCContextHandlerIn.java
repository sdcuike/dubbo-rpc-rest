package com.alibaba.dubbo.rpc.protocol.resteasy.support;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map.Entry;

import com.alibaba.dubbo.rpc.RpcContext;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * @author sdcuike
 *
 *         Create At 2016年4月25日 下午6:39:32
 */
@Sharable
public class RPCContextHandlerIn extends SimpleChannelInboundHandler<FullHttpRequest> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
        RpcContext context = RpcContext.getContext();
        context.setRemoteAddress((InetSocketAddress) ctx.channel().remoteAddress());
        context.setLocalAddress((InetSocketAddress) ctx.channel().localAddress());
        List<Entry<String, String>> entries = msg.headers().entries();
        for (Entry<String, String> entry : entries) {
            context.setAttachment(entry.getKey(), entry.getValue());
        }

        ctx.fireChannelRead(msg);
    }

}
