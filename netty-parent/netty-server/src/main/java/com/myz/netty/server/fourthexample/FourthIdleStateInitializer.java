package com.myz.netty.server.fourthexample;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * @author yzMa
 * @desc
 * @date 2020/2/9 11:29 AM
 * @email 2641007740@qq.com
 */
public class FourthIdleStateInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        ChannelPipeline pipeline = ch.pipeline();
        //
        /**
         * 一旦建立好连接之后【随便找一个客户端】，发送心跳包或者发送任何数据，服务端在特定时间内没有收到读写请求，则服务端触发一个事件。
         * 可用作：集群中节点状态感知或者长连接
         * 使用ThirdChantClient用作测试，超过30秒没有数据发送FourthServer触发IdleStateEvent，FourthIdleState的逻辑是这里的断开连接。
         */
        pipeline.addLast("idleStateHandler",new IdleStateHandler(30,100,50));
        pipeline.addLast("fourthIdleStateHandler",new FourthIdleStateHandler());
    }
}
