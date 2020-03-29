package com.myz.netty.server.firstexample;

import com.myz.common.util.PrintUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * @author yzMa
 * @desc HttpObject为上一个ChannelHandler的返回值
 * @param
 * @date 2020/2/8 4:31 PM
 * @email 2641007740@qq.com
 */
public class FirstServerHttpHandler extends SimpleChannelInboundHandler<HttpObject> {
    /**
     * @Description: 业务回调最重要的方法
     * 可以通过curl或者浏览器当做客户端来测试了，当然uri加上没有用
     * 比如：curl 'http://localhost:8899'
     *
     * curl跟浏览器对比
     * 1）请求头
     * 2）回调方法
     *
     * curl即便是加上Connection: keep-alive也相当于浏览器关闭
     * 浏览器多刷新几次|关闭浏览器看打印
     * @Date: 2020/2/8 5:00 PM
     * @author: yzMa
     * @param:
     * @return:
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject httpObject) throws Exception {

        PrintUtil.print("channelRead0 invoked remoteAddress="+ctx.channel().remoteAddress());//,httpObject="+httpObject);
        if (!(httpObject instanceof HttpRequest)){
            PrintUtil.print("非HttpRequest");
            return;
        }

        HttpRequest httpRequest = (HttpRequest) httpObject;
        if ("/favicon.ico".equals(httpRequest.uri())){
            PrintUtil.print("浏览器图标忽略掉");
            return;
        }

        ByteBuf responseContent = Unpooled.copiedBuffer("First Http response\r\n", CharsetUtil.UTF_8);

        FullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK,
                responseContent);
        response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain;");
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH,responseContent.readableBytes());

        ctx.writeAndFlush(response);//前面是HttpServerCodec 处理的，返回的时候也要符合返回的对 象

//        ctx.close();// 判断 http 1.1  超时时间 等条件后可以显示的关闭连接
    }

    /**
     * handlerAdded
     * channelRegistered
     * channelActive
     *
     * channelRead0
     *
     * ---浏览器中有请求头 Connection:keep-alive 情况下后边不打印  除非关闭浏览器
     * channelInactive
     * channelUnregistered
     *
     */

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        PrintUtil.print("handlerAdded ");
        super.handlerAdded(ctx);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        PrintUtil.print("channelRegistered ");
        super.channelRegistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        PrintUtil.print("channelActive ");
        super.channelActive(ctx);
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        PrintUtil.print("channelInactive ");
        super.channelInactive(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        PrintUtil.print("channelUnregistered ");
        super.channelUnregistered(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        PrintUtil.print("exceptionCaught 需要关闭连接 e="+cause);
        super.exceptionCaught(ctx, cause);
        ctx.channel().close();
    }
}
