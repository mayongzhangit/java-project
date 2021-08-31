package com.myz.netty.server.firstexample;

import com.myz.common.util.PrintUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * @author yzMa
 * @desc
 * @date 2020/2/8 3:59 PM
 * @email 2641007740@qq.com
 */
public class FirstServer {

    public static void main(String[] args) {

        // 一般都带有Nio，这里实际为ThreadPoolExecutor
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {

            // Server端都会有Server开头的
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup,workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new FirstServerHttpChannelInitializer());//基本上我们会重写这个ChannelHandler   ChannelInitializer

            ChannelFuture channelFuture = bootstrap.bind(new InetSocketAddress(8899)).sync();
            PrintUtil.print("First Server 启动成功");

            channelFuture.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
