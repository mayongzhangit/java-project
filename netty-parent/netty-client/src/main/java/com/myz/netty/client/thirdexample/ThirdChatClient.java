package com.myz.netty.client.thirdexample;

import com.myz.common.util.PrintUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;

/**
 * @author yzMa
 * @desc
 * @date 2020/2/8 11:15 PM
 * @email 2641007740@qq.com
 */
public class ThirdChatClient {

    public static void main(String[] args) {


        EventLoopGroup group = new NioEventLoopGroup();

        try {

            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ThirdClientDelimitFrameInitializer());

            ChannelFuture channelFuture = bootstrap.connect(new InetSocketAddress("localhost", 8899)).sync();
            PrintUtil.print("third client connect success wait for input");

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

            String line = null;
            while ( (line = bufferedReader.readLine())!=null ) {
                channelFuture.channel().writeAndFlush(line+"\r\n");
            }
//            channelFuture.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            group.shutdownGracefully();
        }
    }
}
