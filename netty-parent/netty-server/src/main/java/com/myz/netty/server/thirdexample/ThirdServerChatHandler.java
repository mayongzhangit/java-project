package com.myz.netty.server.thirdexample;

import com.myz.common.util.PrintUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @author yzMa
 * @desc
 * @date 2020/2/8 10:35 PM
 * @email 2641007740@qq.com
 */
public class ThirdServerChatHandler extends SimpleChannelInboundHandler<String> {


    // 完全可以使用map替换，而且本身它就是set
    // 一定要是static  因为该类是多例的!!!
    // 一定要是static  因为该类是多例的!!!
    // 一定要是static  因为该类是多例的!!!
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();

        channelGroup.forEach(ch -> {
            if (channel==ch){
                ch.writeAndFlush("[third server self]"+msg+"\r\n");
                return;
            }
            // 消息内容最后加上 \r\n主要是因为DelimiterBasedFrameDecoder
            ch.writeAndFlush("[third server broadcast]"+channel.remoteAddress()+" "+msg+"\r\n");
        });

    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        // 相当于是广播
        channelGroup.writeAndFlush("[third server broadcast]"+channel.remoteAddress()+"上线"+"\r\n");

        channelGroup.add(channel);
        PrintUtil.print("channelId= "+channel.id()+",address="+channel.remoteAddress()+" 连接到服务器"+"，共"+channelGroup.size()+"人在线");
    }


    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.remove(channel);

        // 相当于是广播
        channelGroup.writeAndFlush("[third server]"+channel.remoteAddress()+"下线"+"\r\n");
        PrintUtil.print("channelId= "+channel.id()+",address="+channel.remoteAddress()+" 断开服务器"+"，还剩"+channelGroup.size()+"人在线");

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.channel().close();
    }
}
