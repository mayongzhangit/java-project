package com.myz.netty.server.secondexample;

import com.myz.netty.server.util.PrintUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

/**
 * @author yzMa
 * @desc
 * @date 2020/2/8 9:30 PM
 * @email 2641007740@qq.com
 */
public class SecondStringHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        ctx.channel().writeAndFlush("from server "+ UUID.randomUUID().toString());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        PrintUtil.print("发生异常需要关闭连接");
        ctx.channel().close();
    }
}
