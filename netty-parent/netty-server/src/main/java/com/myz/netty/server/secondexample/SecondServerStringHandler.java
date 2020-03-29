package com.myz.netty.server.secondexample;

import com.myz.common.util.PrintUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

/**
 * @author yzMa
 * @desc
 * @date 2020/2/8 9:30 PM
 * @email 2641007740@qq.com
 */
public class SecondServerStringHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        PrintUtil.print(msg);
        ctx.channel().writeAndFlush("[second server said] "+ UUID.randomUUID().toString());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        PrintUtil.print("发生异常需要关闭连接");
        ctx.channel().close();
    }
}
