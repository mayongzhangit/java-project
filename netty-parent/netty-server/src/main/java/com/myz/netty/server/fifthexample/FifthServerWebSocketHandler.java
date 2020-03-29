package com.myz.netty.server.fifthexample;

import com.myz.common.util.PrintUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.time.LocalDate;

/**
 * @author yzMa
 * @desc
 * @date 2020/2/9 8:44 PM
 * @email 2641007740@qq.com
 */
public class FifthServerWebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        PrintUtil.print(msg);
        ctx.channel().writeAndFlush(new TextWebSocketFrame("[fifth server]:"+ LocalDate.now()));
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        PrintUtil.print("handleAdded "+ctx.channel().id().asLongText());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        PrintUtil.print("handlerRemoved "+ctx.channel().id().asLongText()+" 可能是刷新页面了");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.channel().close();
    }
}
