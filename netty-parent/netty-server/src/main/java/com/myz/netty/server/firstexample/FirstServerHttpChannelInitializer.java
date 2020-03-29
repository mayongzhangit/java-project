package com.myz.netty.server.firstexample;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author yzMa
 * @desc 泛型目前填写SocketChannel
 * @date 2020/2/8 4:18 PM
 * @email 2641007740@qq.com
 */
public class FirstServerHttpChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast("httpServerCodec",new HttpServerCodec());//都是添加的 ChannelHandler
        pipeline.addLast("firstHttpHandler",new FirstServerHttpHandler());//泛型是上个ChannelHandler的返回值
    }
}
