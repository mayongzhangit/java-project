package com.myz.netty.server.secondexample;

import com.myz.common.util.PrintUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LoggingHandler;

import java.net.InetSocketAddress;

/**
 * @author yzMa
 * @desc
 * @date 2020/2/8 9:10 PM
 * @email 2641007740@qq.com
 */
public class SecondServer {

    public static void main(String[] args) {


        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup,workGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler())// handler 是针对于bossGroup来说的，处理连接
                    .childHandler(new SecondServerLengthFrameChannelInitializer())// childHandler是针对于workGroup来说的，处理业务
                    ;

            ChannelFuture channelFuture = serverBootstrap.bind(new InetSocketAddress(8899)).sync();
            PrintUtil.print("second server Started");

            channelFuture.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
