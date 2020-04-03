package com.myz.netty.server.sixthexample;

import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author yzMa
 * @desc
 * @date 2020/3/30 10:40 PM
 * @email 2641007740@qq.com
 */
public class SixthServerPersonDecodeChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(null);

    }
}
