package com.alibaba.dubbo.rpc.protocol.resteasy.support;

import java.util.Map.Entry;
import java.util.Set;

import com.alibaba.dubbo.rpc.RpcContext;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.HttpResponse;

/**
 * @author sdcuike
 *
 *         Create At 2016年4月25日 下午7:05:39
 */
@Sharable
public class RPCContextHandlerOut extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        RpcContext context = RpcContext.getContext();
        HttpResponse response = (HttpResponse) msg;
        Set<Entry<String, String>> entrySet = context.getAttachments().entrySet();
        for (Entry<String, String> entry : entrySet) {
            response.headers().add(RestHeaderUtil.restHeaderPrefix + entry.getKey(), entry.getValue());
        }
    }

}
