package com.myz.netty.server.fifthexample;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author yzMa
 * @desc
 * @date 2020/2/9 8:37 PM
 * @email 2641007740@qq.com
 */
public class FifthServerWebSocketChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        ChannelPipeline pipeline = ch.pipeline();

        // WebSocket是建立在Http之上的，建立完连接之后，进行协议的升级
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new ChunkedWriteHandler());
        pipeline.addLast(new HttpObjectAggregator(8196));

        // WebSocket是建立在Http之上 webSocket("ws://localhost:8899/ws") 状态码 101 转换协议
        // 请求头 Upgrade:websocket   connection: upgrade
        // 响应头 connection: upgrade upgrade: websocket sec-websocket-accept: CuajKpIImMkgftTbb4x8SL10O3M=
        //
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        pipeline.addLast(new FifthServerWebSocketHandler());
    }
}
