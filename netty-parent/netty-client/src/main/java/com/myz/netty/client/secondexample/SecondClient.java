package com.myz.netty.server.secondexample;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * @author yzMa
 * @desc
 * @date 2020/2/8 9:33 PM
 * @email 2641007740@qq.com
 */
public class SecondClient {

    public static void main(String[] args) {

        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(null)// 不需要childHandler，每个客户端自己处理连接和业务逻辑  以为只有一个EventLoopGroup
                    ;
            ChannelFuture channelFuture = bootstrap.connect(new InetSocketAddress("localhost", 8899)).sync();

            channelFuture.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            group.shutdownGracefully();
        }

    }
}
