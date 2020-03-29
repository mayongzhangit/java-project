package com.myz.netty.server.fourthexample;

import com.myz.common.util.PrintUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @author yzMa
 * @desc
 * @date 2020/2/9 11:57 AM
 * @email 2641007740@qq.com
 */
public class FourthIdleStateHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        PrintUtil.print("channelRead msg="+msg);
    }

    /**
     * {@link io.netty.handler.timeout.IdleStateHandler}
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        if(evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent)evt;
            PrintUtil.print("idleState event="+event.state().name()+" triggered server close channel");
            ctx.channel().close();
        }

    }
}
