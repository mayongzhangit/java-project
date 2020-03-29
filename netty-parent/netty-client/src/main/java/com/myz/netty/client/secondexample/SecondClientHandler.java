package com.myz.netty.client.secondexample;

import com.myz.common.util.PrintUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.time.LocalDate;

/**
 * @author yzMa
 * @desc
 * @date 2020/2/8 9:49 PM
 * @email 2641007740@qq.com
 */
public class SecondClientHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

        PrintUtil.print(msg);
        ctx.writeAndFlush("[second client said ]"+ LocalDate.now());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush("[second client channelActive say] "+"hi");
        super.channelActive(ctx);
    }
}
