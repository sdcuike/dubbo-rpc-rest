package com.alibaba.dubbo.rpc.protocol.resteasy.support;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

/**
 * @author sdcuike
 *
 *         Create At 2016年4月25日 下午7:05:39
 */
public class RPContextHandlerOut extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        // TODO Auto-generated method stub
        super.write(ctx, msg, promise);
    }

}
